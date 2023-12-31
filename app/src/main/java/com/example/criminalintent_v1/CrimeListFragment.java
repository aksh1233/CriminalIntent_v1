package com.example.criminalintent_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class CrimeListFragment extends Fragment {

   private RecyclerView mCrimeRecyclerView;
   private CrimeAdapter mCrimeAdapter;

   private boolean mSubtitleVisible;
   private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


   @Override
    public void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);

       if(savedInstanceState!=null)
           mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
   }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState)
   {
       View view = inflater.inflate(R.layout.fragment_crime_list,container,false);

       mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
       mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       updateUI();
       return view;
   }

   @Override
   public void onResume()
   {
       super.onResume();
       updateUI();
   }

   @Override
   public void onSaveInstanceState(Bundle outState)
   {
       super.onSaveInstanceState(outState);
       outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
   }

   public void updateUI()
   {
       CrimeLab crimeLab = CrimeLab.get(getActivity());
       List<Crime> crimes = crimeLab.getCrimes();

       if(mCrimeAdapter==null){
       mCrimeAdapter = new CrimeAdapter(crimes);
       mCrimeRecyclerView.setAdapter(mCrimeAdapter);}

       else
           mCrimeAdapter.setCrimes(crimes);
           mCrimeAdapter.notifyDataSetChanged();
      updateSubtitle();
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
   {
       super.onCreateOptionsMenu(menu,inflater);
       inflater.inflate(R.menu.fragment_crime_list,menu);

       MenuItem subTitleItem = menu.findItem(R.id.show_subtitle);
       if(mSubtitleVisible)
           subTitleItem.setTitle(R.string.hide_subtitle);
       else
           subTitleItem.setTitle(R.string.show_subtitle);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
       switch (item.getItemId())
       {
           case R.id.new_crime:
               Crime crime = new Crime();
               CrimeLab.get(getActivity()).addCrime(crime);
               Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());
               startActivity(intent);
               return true;


           case R.id.show_subtitle:
               mSubtitleVisible = !mSubtitleVisible;
               getActivity().invalidateOptionsMenu();
               updateSubtitle();
               return true;

           default:
               return super.onOptionsItemSelected(item);
       }
   }

   private void updateSubtitle()
   {
       CrimeLab crimeLab = CrimeLab.get(getActivity());
       int crimeCount = crimeLab.getCrimes().size();
       String count = getString(R.string.subtitle_format,crimeCount);

       if(!mSubtitleVisible)
           count = null;

       AppCompatActivity activity = (AppCompatActivity) getActivity();
       activity.getSupportActionBar().setSubtitle(count);
   }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

       private List<Crime> mCrimes;
       public CrimeAdapter(List<Crime> crimes)
       {
           mCrimes = crimes;
       }

       @Override
       public int getItemViewType(int position)
       {
           Crime crime = mCrimes.get(position);
           if(crime.isRequirespolice()==true)
               return 1;
           else
               return 0;
       }


       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int ViewType)
       {
           LayoutInflater inflater = LayoutInflater.from(getActivity());
          if(ViewType==0)
              return new CrimeHolder(inflater,parent);
          else
              return new CrimeHolder1(inflater,parent);
       }
       @Override
       public void  onBindViewHolder(RecyclerView.ViewHolder holder,int position)
       {
           Crime crime = mCrimes.get(position);
           switch(holder.getItemViewType()){

               case 0: CrimeHolder crimeHolder = (CrimeHolder) holder;
                        crimeHolder.bind(crime);
                        break;

               case 1: CrimeHolder1 crimeHolder1 = (CrimeHolder1) holder;
                       crimeHolder1.bind(crime);
                       break;
           }


       }
       @Override
       public int getItemCount()
       {
           return mCrimes.size();
       }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }


   }
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private ImageView mSolvedImageView;
        private Crime mCrime;
        public CrimeHolder(LayoutInflater inflater,ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.handcuffs);
        }
        @Override
        public void onClick(View view)
        {

            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);

        }

        public void bind(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private class CrimeHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        public CrimeHolder1(LayoutInflater inflater,ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_crime1,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }
        @Override
        public void onClick(View view)
        {
            Toast.makeText(getActivity(),mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }

        public void bind(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }
    }



}
