package com.example.loanapplication;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Loan> mUploads;
    private OnItemClickListener mListener;

    public LoanAdapter(Context mContext, List<Loan> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Loan uploadCurrent = mUploads.get(position);

        holder.mTvLoanAmont.setText(uploadCurrent.getLoanAmount());
        holder.mTvLoanPeriod.setText(uploadCurrent.getLoanPeriod());
        holder.mTvMonthlyPayment.setText(uploadCurrent.getMonthlyResult());
        holder.mTvTotalPayment.setText(uploadCurrent.getTotalResult());

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {


        TextView mTvLoanAmont, mTvLoanPeriod, mTvMonthlyPayment, mTvTotalPayment;
   
        public ImageViewHolder(View itemView) {
            super(itemView);

            mTvLoanAmont = itemView.findViewById(R.id.tv_loanamount);
            mTvLoanPeriod = itemView.findViewById(R.id.tv_loanperiod);
            mTvMonthlyPayment = itemView.findViewById(R.id.tv_montlypayment);
            mTvTotalPayment = itemView.findViewById(R.id.tv_totalpayment);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
        // Handle Menu Items
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1,"Verify code");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onVerifyClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);

        void onVerifyClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setSearchOperation(List<Loan> newList){
        mUploads = new ArrayList<>();
        mUploads.addAll(newList);
        notifyDataSetChanged();
    }
}