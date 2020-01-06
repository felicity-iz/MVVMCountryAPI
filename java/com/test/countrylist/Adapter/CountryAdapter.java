package com.test.countrylist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.countrylist.CountryDetailActivity;
import com.test.countrylist.Model.Country;
import com.test.countrylist.R;

import java.text.DecimalFormat;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Country> countries;
    private static int TYPE_EQUAL = 1;
    private static int TYPE_UNEQUAL = 2;
    private static int TYPE_VUNEQUAL = 3;
    private static int TYPE_NODATA = 4;

    public CountryAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView countryName;
        private TextView nativeName;
        private TextView area;
        private TextView gini;
        private Country_ItemClickListener itemClickListener;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country);
            nativeName = itemView.findViewById(R.id.nativeName);
            area = itemView.findViewById(R.id.area);
            gini = itemView.findViewById(R.id.gini);

            itemView.setOnClickListener(this);
        }

        private void setGiniDetails(Country country){
            countryName.setText(country.getName());
            nativeName.setText(country.getNativeName());
            //int gini = (int) Math.ceil(country.getArea());
            DecimalFormat df = new DecimalFormat("###,###");
            String formatted = df.format(country.getArea());
            area.setText(formatted);
            //area.setText(Float.toString(country.getArea()));
            if(country.getGini() != 0) {
                //String formattedFloat = String.format("%.2f", country.getGini());
                int giniValue = (int) Math.ceil(country.getGini());
                gini.setText(Integer.toString(giniValue));

            }else{
                gini.setText("N/A");
            }

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }

        public void setItemClickListener(Country_ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Country currentCountry = countries.get(position);
        if(currentCountry.getGini() != 0) {
            if (currentCountry.getGini() <= 30.0) {
                return TYPE_EQUAL;
            } else if (currentCountry.getGini() <= 40.0) {
                return TYPE_UNEQUAL;
            } else {
                return TYPE_VUNEQUAL;
            }
        }else{
            return TYPE_NODATA;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_EQUAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false);
            return new CountryViewHolder(view);
        }else if(viewType == TYPE_UNEQUAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item_unequal,parent,false);
            return new CountryViewHolder(view);
        }else if(viewType == TYPE_VUNEQUAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item_vunequal,parent,false);
            return new CountryViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item_nodata,parent,false);
            return new CountryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Country currentCountry = countries.get(position);
        ((CountryViewHolder)holder).setGiniDetails(countries.get(position));

        ((CountryViewHolder) holder).setItemClickListener(new Country_ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i=new Intent(view.getContext(), CountryDetailActivity.class);

                i.putExtra("CountryName",countries.get(position).getName());
                i.putExtra("NativeName",countries.get(position).getNativeName());
                i.putExtra("Borders",countries.get(position).getBorders());
                i.putExtra("Flag", countries.get(position).getFlag());

                view.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


}
