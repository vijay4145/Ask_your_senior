<<<<<<<< HEAD:app/src/main/java/com/example/askyoursenior/Profile_Fragments/ProfileFragment.java
<<<<<<<< HEAD:app/src/main/java/com/example/askyoursenior/Profile_Fragments/ProfileFragment.java
package com.example.askyoursenior.Profile_Fragments;
========
package com.example.askyoursenior.homepage_fragments.profilepackage;
>>>>>>>> 64253d1dae023ab39c13abfb12ed18c1e244c74e:app/src/main/java/com/example/askyoursenior/homepage_fragments/profilepackage/ProfileFragment.java
========
package com.example.askyoursenior.homepage_fragments.profile_package;
>>>>>>>> origin/Profile_Creation:app/src/main/java/com/example/askyoursenior/homepage_fragments/profile_package/ProfileFragment.java

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.askyoursenior.R;


public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //beta code here
    }
}