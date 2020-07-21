package com.avijit.rms1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.model.User;
import com.avijit.rms1.ui.MainDashboard;
import com.avijit.rms1.ui.UserCRUD;
import com.avijit.rms1.utils.AppUtils;
import com.avijit.rms1.viewmodel.UserCrudViewModel;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

/**
 * Created by Avijit Acharjee on 7/18/2020 at 11:58 PM.
 * Email: avijitach@gmail.com.
 */
public class UserCrudRecyclerViewAdapter extends RecyclerView.Adapter<UserCrudRecyclerViewAdapter.ViewHolder>{
    private List<User> userList;
    private Context context;
    private UserCrudViewModel viewModel;
    private AppUtils appUtils;
    public UserCrudRecyclerViewAdapter(Context context,List<User> userList){
        this.userList = userList;
        this.context = context;
        viewModel= ViewModelProviders.of((UserCRUD)context).get(UserCrudViewModel.class);
        appUtils = new AppUtils(context);
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list,parent,false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(userList.get(position).getName());
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setPositiveButton("Yes",(dialog, which) -> {
                        appUtils.dialog.show();
                        viewModel.delete(userList.get(position).getId()).observe((UserCRUD) context,response->{
                            appUtils.dialog.dismiss();

                            if(response.isNetworkIsSuccessful()){
                                userList.remove(position);
                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("No",(dialog, which) -> {

                    })
                    .setMessage("Are you sure to delete?")
                    .create()
                    .show();
        });
        holder.updateButton.setOnClickListener(v -> {
            DialogFragment dialogFragment=  new UpdateDialogFragment(userList.get(position));
            FragmentTransaction ft = ((UserCRUD) context ).getSupportFragmentManager().beginTransaction();
            Fragment prev =((UserCRUD) context ). getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(ft,"update");
        });
        holder.viewButton.setOnClickListener(v-> {
            DialogFragment dialogFragment=  new ViewUserDialogFragment(userList.get(position));
            FragmentTransaction ft = ((UserCRUD) context ).getSupportFragmentManager().beginTransaction();
            Fragment prev =((UserCRUD) context ). getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(ft,"update");
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private ImageView deleteButton,updateButton,viewButton;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
            viewButton = itemView.findViewById(R.id.view_button);
        }

    }
    public static class UpdateDialogFragment extends DialogFragment {
        EditText fullNameEditText,emailEditText,phoneEditText,nidEditText,passwordEditText;
        Spinner typeSpinner;
        TextView goButton;
        UserCrudViewModel viewModel;
        User user;
        public UpdateDialogFragment(User user){
            this.user = user;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_user_update,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UserCrudViewModel.class);
            initViews(view);
            setInitialValue();
            AppUtils appUtils = new AppUtils(getContext());
            goButton.setOnClickListener(v->{
                User newUser = new User();
                newUser.setName(fullNameEditText.getText().toString());
                newUser.setEmail(emailEditText.getText().toString());
                newUser.setPhone(phoneEditText.getText().toString());
                newUser.setNid(nidEditText.getText().toString());
                newUser.setPassword(passwordEditText.getText().toString());
                newUser.setTbl_user_types_id((typeSpinner.getSelectedItemPosition()-1)+"");
                appUtils.dialog.show();
                viewModel.update(user.getId(),newUser).observe(getActivity(),response->{
                    appUtils.dialog.dismiss();
                    if(response.isNetworkIsSuccessful()){
                        Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
        private void initViews(View view){
            fullNameEditText = view.findViewById(R.id.full_name_edit_text);
            emailEditText = view.findViewById(R.id.email_edit_text);
            phoneEditText = view.findViewById(R.id.phone_edit_text);
            nidEditText = view.findViewById(R.id.nid_edit_text);
            passwordEditText = view.findViewById(R.id.password_edit_text);
            typeSpinner = view.findViewById(R.id.type_spinner);
            goButton = view.findViewById(R.id.go);
        }
        private void setInitialValue(){
            fullNameEditText.setText(user.getName());
            emailEditText.setText(user.getEmail());
            phoneEditText.setText(user.getPhone());
            nidEditText.setText(user.getNid());
            passwordEditText.setText(user.getPassword());
            typeSpinner.setSelection(Integer.parseInt(user.getTbl_user_types_id())+1);

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            super.onActivityCreated(savedInstanceState);
        }
    }
    public static class ViewUserDialogFragment extends DialogFragment{
        User user;
        public ViewUserDialogFragment(User user){
            this.user=user;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_user_details,container,false);
        }
    }
}
