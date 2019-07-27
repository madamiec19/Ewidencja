package com.example.ewidencja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ewidencja.Database.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveHolder> {
    private List<Move> moves = new ArrayList<>();

    @NonNull
    @Override
    public MoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.move_item, parent, false);

        return new MoveHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoveHolder holder, int position) {
        Move currentMove = moves.get(position);
        holder.tvCar.setText(currentMove.getCar());
        holder.tvKmStart.setText(String.valueOf(currentMove.getKmStart()));
        holder.tvKmStop.setText(String.valueOf(currentMove.getKmStop()));
        holder.tvValue.setText(String.valueOf(currentMove.getValue()));

    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public void setMoves(List<Move> moves){
        this.moves = moves;
        notifyDataSetChanged();
    }

    public Move getMoveAt(int position) {
        return moves.get(position);
    }

    class MoveHolder extends RecyclerView.ViewHolder{
        private TextView tvCar;
        private TextView tvKmStart;
        private TextView tvKmStop;
        private TextView tvValue;

        public MoveHolder(View itemView){
            super(itemView);

            tvCar = itemView.findViewById(R.id.tvCar);
            tvKmStart = itemView.findViewById(R.id.tvKmStart);
            tvKmStop = itemView.findViewById(R.id.tvKmStop);
            tvValue = itemView.findViewById(R.id.tvValue);
        }
    }


}
