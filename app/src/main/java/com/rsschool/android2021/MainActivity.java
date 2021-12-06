package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rsschool.android2021.ui.first.FirstFragment;
import com.rsschool.android2021.ui.second.SecondFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ActionPerformedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    private void openSecondFragment(int result) {
        final Fragment secondFragment = SecondFragment.newInstance(result);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override
    public void onActionPerformedOne(int previousNumber) {
        openFirstFragment(previousNumber);
    }

    @Override
    public void onActionPerformedTwo(int result) {
        openSecondFragment(result);
    }
}
