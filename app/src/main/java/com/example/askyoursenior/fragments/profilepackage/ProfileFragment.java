package com.example.askyoursenior.fragments.profilepackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.askyoursenior.LoginActivity;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.FragmentProfileBinding;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.example.askyoursenior.shared_preferences_operation.UserDetailFromLocalDb;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding fragmentProfileBinding;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fragmentProfileBinding.applicationInfoButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ApplicationInfo.class);
            startActivity(intent);
        });

        fragmentProfileBinding.accountSettingLayout.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
            startActivity(intent);
        });

        fragmentProfileBinding.githubIcon.setOnClickListener(view13 -> {
            String link = UserDetailFromLocalDb.getGithubId(view.getContext());
            openLinkInBrowser(link);
        });

        fragmentProfileBinding.linkedinIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = UserDetailFromLocalDb.getLinkedinId(view.getContext());
                openLinkInBrowser(link);
            }
        });

        fragmentProfileBinding.whatsappIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkInWhatsapp();
            }
        });

        fragmentProfileBinding.applicatioInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragmentProfileBinding.getRoot().getContext(), ApplicationInfo.class);
                fragmentProfileBinding.getRoot().getContext().startActivity(intent);
            }
        });

        fragmentProfileBinding.logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragmentProfileBinding.getRoot().getContext(), LoginActivity.class);
                UserDetailFromLocalDb.setLogout(fragmentProfileBinding.getRoot().getContext());
                fragmentProfileBinding.getRoot().getContext().startActivity(intent);
            }
        });

        fragmentProfileBinding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragmentProfileBinding.getRoot().getContext(), LoginActivity.class);
                UserDetailFromLocalDb.setLogout(fragmentProfileBinding.getRoot().getContext());
                fragmentProfileBinding.getRoot().getContext().startActivity(intent);
            }
        });



    }

    private void openLinkInWhatsapp() {
        String phonenum = UserDetailFromLocalDb.getPhoneNumber(fragmentProfileBinding.getRoot().getContext());
        if(phonenum.equals("") || phonenum.equals(SharedPreferenceDb.NOT_FOUND_ERROR)) {
            Toast.makeText(fragmentProfileBinding.getRoot().getContext(), "Please Complete Your Profile", Toast.LENGTH_SHORT).show();
            return ;
        }
        String msg = "Hello this is " + UserDetailFromLocalDb.getUserName(fragmentProfileBinding.getRoot().getContext()) + " looking forward to hear from you...";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.putExtra("jid", phonenum + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    void openLinkInBrowser(String link){
        if(link.equals(SharedPreferenceDb.NOT_FOUND_ERROR) || link.equals("")) {
            Toast.makeText(fragmentProfileBinding.getRoot().getContext(), "Please Complete Your Profile", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        fragmentProfileBinding.getRoot().getContext().startActivity(browserIntent);
    }
}