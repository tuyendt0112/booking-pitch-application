package com.example.BookingPitch.fragment.adminfragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.admin.CategoryManagerActivity;
import com.example.BookingPitch.adapter.admin.ManagerAdapter;
import com.example.BookingPitch.adapter.admin.SpinnerLoaiNVAdapter;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.facade.CheckEmployeeAddingFacade;
import com.example.BookingPitch.model.Manager;
import com.example.BookingPitch.model.ManagerCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NhanVienFragment extends Fragment {

    private List<Manager> managerList;
    private RecyclerView recyclerView;
    private ImageView imgSearch;
    private EditText edtSearch;
    private Button btnShowLoaiNhanVien;
    private ManagerAdapter adapter;
    FloatingActionButton btnAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerList = MyDatabase.getInstance(getContext()).managerDAO().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_staff);
        imgSearch = view.findViewById(R.id.img_search_staff_nhanvien_fragment);
        edtSearch = view.findViewById(R.id.edt_searchNameStaff_activity_staff);
        btnShowLoaiNhanVien = view.findViewById(R.id.btn_show_loaiNhanvien);
        btnAdd = view.findViewById(R.id.btn_create_dialog_add_staff);

        btnShowLoaiNhanVien.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), CategoryManagerActivity.class);
            getContext().startActivity(intent);
            Animatoo.INSTANCE.animateSlideLeft(getContext());
        });
        btnAdd.setOnClickListener(v->{
            createDialogAdd();
        });
        imgSearch.setOnClickListener(v->{
            if(edtSearch.getText().toString().equals("")){
                adapter.setData(managerList);
            }else{
                adapter.setData(MyDatabase.getInstance(getContext()).managerDAO().getManagerWithName("%"+edtSearch.getText().toString()+"%"));
            }
        });

        adapter = new ManagerAdapter(managerList,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    public void createDialogAdd(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_nv);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtName = dialog.findViewById(R.id.edt_name_dialog_add_nhanvien);
        EditText edtBankNumber = dialog.findViewById(R.id.edt_banknumber_dialog_add_nhanvien);
        EditText edtBankName = dialog.findViewById(R.id.edt_bankname_dialog_add_nhanvien);
        EditText edtPass = dialog.findViewById(R.id.edt_pass_dialog_add_nhanvien);
        EditText edtPass2 = dialog.findViewById(R.id.edt_pass2_dialog_add_nhanvien);

        Spinner spinner = dialog.findViewById(R.id.spinner_chucvu_dialog_add_nhanvien);
        List<ManagerCategory> listAllStaff = MyDatabase.getInstance(getContext()).managerCategoryDAO().getAllStaff();
        SpinnerLoaiNVAdapter spAdapter = new SpinnerLoaiNVAdapter(getContext(),R.layout.line_spinner_loai_nv,listAllStaff);
        spinner.setAdapter(spAdapter);

        EditText edtSalary = dialog.findViewById(R.id.edt_salary_dialog_add_nhanvien);
        EditText edtPhone = dialog.findViewById(R.id.edt_phone_dialog_add_nhanvien);

        TextView tvCheckName = dialog.findViewById(R.id.tv_check_name_dialog_add_nhanvien);
        TextView tvCheckPass1 = dialog.findViewById(R.id.tv_check_pass_dialog_add_nhanvien);
        TextView tvCheckPass2 = dialog.findViewById(R.id.tv_check_pass2_dialog_add_nhanvien);
        TextView tvCheckPhone = dialog.findViewById(R.id.tv_check_phone_dialog_add_nhanvien);

        RadioButton rdoLamViec = dialog.findViewById(R.id.rdo_lamviec_add_nv);

        Button btnThem = dialog.findViewById(R.id.btn_add_nhanvien);
        btnThem.setOnClickListener(v->{
            String phone = edtPhone.getText().toString();
            String name = edtName.getText().toString();
            String pass = edtPass.getText().toString();
            String pass2 = edtPass2.getText().toString();
            String bankNumber = edtBankNumber.getText().toString();
            String bankName = edtBankName.getText().toString();
            int salary;

            CheckEmployeeAddingFacade EmployeeFacade = new CheckEmployeeAddingFacade(phone,name,pass,pass2);
            if(!EmployeeFacade.AddingEmployeeFacade())
            {
                tvCheckPhone.setText("* Invalid information");
                tvCheckPhone.setVisibility(View.VISIBLE);
            }
            else{
                try{
                    salary = Integer.parseInt(edtSalary.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(getContext(), "Salary must be number", Toast.LENGTH_SHORT).show();
                    return;
                }
                Manager manager =  Manager.getInstance();
                if(MyDatabase.getInstance(getContext()).managerDAO().getManagerWithPhone(phone,-1).size()==0) {
                    if(rdoLamViec.isChecked()) manager.setStatus(MyApplication.DANG_LAM);
                    else manager.setStatus(MyApplication.NGHI_VIEC);
                    manager.setPhone(phone);
                    manager.setName(name);
                    manager.setPassword(pass);
                    manager.setBankName(bankName);
                    manager.setBankNumber(bankNumber);
                    manager.setSalary(salary);
                    if(listAllStaff.size() <= 0){
                        Toast.makeText(getContext(), "Need more type of position", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        manager.setPosition(listAllStaff.get(spinner.getSelectedItemPosition()).getId());
                    }

                    MyDatabase.getInstance(getContext()).managerDAO().insert(manager);
                    Toast.makeText(getContext(), "Successfully added staff", Toast.LENGTH_SHORT).show();
                    managerList = MyDatabase.getInstance(getContext()).managerDAO().getAll();
                    adapter.setData(managerList);

                    dialog.dismiss();
                }else{
                    tvCheckPhone.setText("* Phone number already exists");
                    invisible(tvCheckName,tvCheckPhone,tvCheckPass1,tvCheckPass2);
                    tvCheckPhone.setVisibility(View.VISIBLE);
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void invisible(TextView...tvs){
        for(TextView tv : tvs){
            tv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        managerList = MyDatabase.getInstance(getContext()).managerDAO().getAll();
        adapter.setData(managerList);
    }
}
