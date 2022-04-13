package com.example.appberita.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appberita.R;
import com.example.appberita.adapter.NoteAdapter;
import com.example.appberita.model.Note;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Note> list = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton btnAdd = findViewById(R.id.btn_add);

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());


        progressDialog = new ProgressDialog(NoteActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        noteAdapter = new NoteAdapter(getApplicationContext(), list);
        noteAdapter.setDialog(pos -> {
            final CharSequence[] dialogItem = {"Edit", "Hapus"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(NoteActivity.this);
            dialog.setItems(dialogItem, (dialogInterface, i) -> {
                switch (i){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                        intent.putExtra("id", list.get(pos).getId());
                        intent.putExtra("title", list.get(pos).getTitle());
                        intent.putExtra("description", list.get(pos).getDescription());
                        startActivity(intent);
                        break;
                    case 1:
                        deleteData(list.get(pos).getId());
                        break;
                }
            });
            dialog.show();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(noteAdapter);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditorActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    // Tambah Data
    private void getData(){
        progressDialog.show();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Note note = new Note(document.getString("title"), document.getString("description"));
                            note.setId(document.getId());
                            list.add(note);
                        }
                        noteAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
    }

    private void deleteData(String id){
        progressDialog.show();
        db.collection("users").document(id)
                .delete()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Data gagal di hapus!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    getData();
                });
    }

    private void onComplete(Task<QuerySnapshot> task) {
        list.clear();
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Note note = new Note(document.getString("title"), document.getString("description"));
                note.setId(document.getId());
                list.add(note);
            }
            noteAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
}
