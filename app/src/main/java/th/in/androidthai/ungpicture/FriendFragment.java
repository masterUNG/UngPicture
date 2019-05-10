package th.in.androidthai.ungpicture;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {


    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create RecyclerView
        createRecyclerView();


    }   // Main Method

    private void createRecyclerView() {

        final RecyclerView recyclerView = getView().findViewById(R.id.recyclerFriend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User");

        final int[] ints = {0};

        final ArrayList<String> nameStringArrayList = new ArrayList<>();
        final ArrayList<String> emailStringArrayList = new ArrayList<>();
        final ArrayList<String> iconStringArrayList = new ArrayList<>();

        final ArrayList<UserModel> userModelArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    UserModel userModel = dataSnapshot1.getValue(UserModel.class);
                    userModelArrayList.add(userModel);

                    UserModel userModel1 = userModelArrayList.get(ints[0]);
                    nameStringArrayList.add(userModel1.getName());
                    emailStringArrayList.add(userModel1.getEmail());
                    iconStringArrayList.add(userModel1.getImage());

                    ints[0] += 1;

                }   // for

                FriendAdapter friendAdapter = new FriendAdapter(getActivity(), iconStringArrayList,
                        nameStringArrayList, emailStringArrayList, new OnClickItem() {
                    @Override
                    public void onClickItem(View view, int position) {
                        Log.d("9MayV1", "You click at position = " + position);
                    }
                });
                recyclerView.setAdapter(friendAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }   // createRecyclerView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

}
