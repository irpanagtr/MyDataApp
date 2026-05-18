package com.example.mydataapp;import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etNim, etNama, etProdi, etKelas, etAlamat, etEmail;
    Button btnTambah, btnLogout;
    ListView listViewData;
    ArrayList<String> dataMahasiswa;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inisialisasi semua View
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etProdi = findViewById(R.id.etProdi);
        etKelas = findViewById(R.id.etKelas);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        btnTambah = findViewById(R.id.btnTambah);
        btnLogout = findViewById(R.id.btnLogout);
        listViewData = findViewById(R.id.listViewData);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // 2. Setup ListView
        dataMahasiswa = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataMahasiswa);
        listViewData.setAdapter(adapter);

        // 3. Tombol Tambah Data
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil string dari input
                String nim = etNim.getText().toString();
                String nama = etNama.getText().toString();
                String prodi = etProdi.getText().toString();
                String kelas = etKelas.getText().toString();
                String alamat = etAlamat.getText().toString();
                String email = etEmail.getText().toString();

                // Validasi: Minimal NIM dan Nama harus diisi
                if (!nim.isEmpty() && !nama.isEmpty()) {

                    // Gabungkan semua data menjadi satu string (Per baris agar rapi)
                    String info = "NIM: " + nim +
                            "\nNama: " + nama +
                            "\nProdi: " + prodi +
                            "\nKelas: " + kelas +
                            "\nAlamat: " + alamat +
                            "\nEmail: " + email;

                    // Tambahkan ke List (Hanya satu kali agar tidak duplikat)
                    dataMahasiswa.add(info);

                    // Refresh ListView
                    adapter.notifyDataSetChanged();

                    // Kosongkan semua field Input (Agar bisa input data baru)
                    etNim.setText("");
                    etNama.setText("");
                    etProdi.setText("");
                    etKelas.setText("");
                    etAlamat.setText("");
                    etEmail.setText("");

                    Toast.makeText(MainActivity.this, "Data Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "NIM dan Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 4. Tombol Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hapus session login di SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Kembali ke Login
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}