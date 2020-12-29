package com.ehb.androiddevapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ehb.androiddevapp.R;
import com.ehb.androiddevapp.domain.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context applicationContext;
    List<Address> myAddressList;
    SelectedAddress selectedAddress;
    private RadioButton mySelectedRadioButton;
    public AddressAdapter(Context applicationContext, List<Address> myAddressList, SelectedAddress selectedAddress) {
        this.applicationContext=applicationContext;
        this.myAddressList=myAddressList;
        this.selectedAddress= selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.single_address_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myAddress.setText(myAddressList.get(position).getAddress());
        holder.myRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Address address:myAddressList){
                    address.setSelected(false);
                }
                myAddressList.get(position).setSelected(true);

                if(mySelectedRadioButton!=null){
                    mySelectedRadioButton.setChecked(false);
                }
                mySelectedRadioButton = (RadioButton) v;
                mySelectedRadioButton.setChecked(true);
                selectedAddress.setAddress(myAddressList.get(position).getAddress());
            }
        });




    }

    @Override
    public int getItemCount() {
        return myAddressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView myAddress;
        private RadioButton myRadio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myAddress=itemView.findViewById(R.id.address_add);
            myRadio=itemView.findViewById(R.id.select_address);
        }
    }


    public interface SelectedAddress {
        public void setAddress(String s);
    }
}
