package com.example.criminalintent_v1;

import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment()
    {
        return new CrimeListFragment();
    }
}
