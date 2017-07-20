package com.tcc.natha.gerenciadordd;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.models.Personagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.models.Personagem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PersonagemContent {

    private Personagem pers;

    private FirebaseUser user;
    private DatabaseReference mPersReference;
    private DatabaseReference mDatabase;

    /**
     * Um array dos personagem para serem visualizados na lista.
     */
    public static final List<PersonagemItem> ITEMS = new ArrayList<PersonagemItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PersonagemItem> ITEM_MAP = new HashMap<String, PersonagemItem>();

    private static final int COUNT = 25;
/*
    pers = new Personagem(user.getUid());


    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println("user dataSnapshot.getKey: " + dataSnapshot.getKey());
            ArrayList<String> personagemKeys = new ArrayList();
            for (DataSnapshot child: dataSnapshot.getChildren()) {
                personagemKeys.add(child.getKey());
            }
            for (String string: personagemKeys) {
                System.out.println("personagemKeys: " +string);
         //       pers.setCarisma(string);
         //       mCarismaField.setText(pers.getCarisma());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }

    };
        mDatabase.child("Users").child(user.getUid()).child("personagens").addValueEventListener(postListener);

   */
//###################

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPersonagemItem(i));
        }
    }

    private static void addItem(PersonagemItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
//###################

    private static PersonagemItem createPersonagemItem(int position) {
        return new PersonagemItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class PersonagemItem {
        public final String id;
        public final String content;
        public final String details;

        public PersonagemItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }

};


