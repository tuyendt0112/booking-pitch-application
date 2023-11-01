package com.example.BookingPitch.activity.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.admin.MainActivity;
import com.example.BookingPitch.activity.admin.ShowCaThiDauActivity;
import com.example.BookingPitch.database.MyDatabase;
import com.example.BookingPitch.model.Customer;
import com.example.BookingPitch.model.MyTime;
import com.example.BookingPitch.model.Order;
import com.example.BookingPitch.model.OrderDetails;
import com.example.BookingPitch.model.Pitch;
import com.example.BookingPitch.model.PithCategory;
import com.example.BookingPitch.model.ServiceBall;
import com.example.BookingPitch.model.TimeOrderDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.example.BookingPitch.strategy.Dialog.MoMoDialog;
import com.example.BookingPitch.strategy.PayByMoMo;
import com.example.BookingPitch.strategy.PayByPayPal;
import com.example.BookingPitch.strategy.PayByZaloPay;
import com.example.BookingPitch.strategy.PaymentContext;
import com.example.BookingPitch.strategy.PaymentStrategy;

public class UserDatSanChiTietActivity extends AppCompatActivity {

    public static int REQUEST_CODE_SERVICE = 2;
    TextView tv_tensan, tvMocTg, tvSoluotCapNhat, tvShow,
            tvDate, tvSanBongMoney, tvAllMoney, tvShowCaThiDau, tvChiPhi;
    Button  btnDatSan;

    public static boolean CHECK_OUT = false;
    ImageView imgBack;
    List<ImageView> listSelect = new ArrayList<>();

    int[] typeSelect = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    ImageView imgSelect1, imgSelect2, imgSelect3, imgSelect4, imgSelect5, imgSelect6;
    ImageView imgSelect7, imgSelect8, imgSelect9, imgSelect10, imgSelect11, imgSelect12;
    private RadioGroup radioGroup;
    Order order;
    Pitch pitch;
    PithCategory categoryPitch;
    Customer customer;
    List<ServiceBall> listService = new ArrayList<>();
    List<Integer> numberOfService = new ArrayList<>();

    public static Boolean CHECKOUT = false;

    int totalMoneyService;
    int totalMoneyPitch;
    int chiPhiKhac;
    String datePlay;
    String dateCreate;
    boolean isUpdate = false;

    int type_add = 0;
    int type_addGray = 1;
    int type_full = 2;
    int type_cancel = 3;
    int type_cancel_gray = 4;

    int count = 0;
    int maxCount = 5;

    boolean canEdit = true;


    private static PaymentContext paymentContext = new PaymentContext();
    private static PaymentStrategy paymentstrategy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dat_san_chi_tiet);
        initView();
        setUpImageSelect();

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.rdo_payment_momo:
                        paymentstrategy = new PayByMoMo();
                        Toast.makeText(getApplicationContext(), "MoMo ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rdo_payment_paypal:
                        paymentstrategy = new PayByPayPal();
                        Toast.makeText(getApplicationContext(), "Paypal ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rdo_payment_zalopay:
                        paymentstrategy = new PayByZaloPay();
                        Toast.makeText(getApplicationContext(), "Zalopay", Toast.LENGTH_SHORT).show();
                        break;
                    default :
                        paymentstrategy = new PayByMoMo();
                        Toast.makeText(getApplicationContext(), "MoMo ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        //paymentContext.ProcessPayment(paymentstrategy);
        MainActivity.ID_MAX_ORDER = MyDatabase.getInstance(this).orderDAO().getIdMax();

        order = (Order) getIntent().getSerializableExtra("ORDER");
        customer = UserMainActivity.customer;

        setOnCLickForView();
        if (order != null) {
            count = order.getSoCa();
            if (order.getStatus() == MyApplication.NGHI_STATUS || order.getStatus() == MyApplication.DANG_STATUS) {
                setOnClickForImageView();
                tvDate.setEnabled(false);
                tvDate.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            } else if (order.getStatus() == MyApplication.CHUA_STATUS) {
                setOnClickForImageView();
            } else if (order.getStatus() == MyApplication.DA_STATUS || order.getStatus() == MyApplication.HUY_STATUS) {
                //btnServiceDetails.setEnabled(false);
                btnDatSan.setEnabled(false);
                tvDate.setEnabled(false);
                btnDatSan.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                //btnServiceDetails.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                tvDate.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                canEdit = false;
            }

            isUpdate = true;
            pitch = MyDatabase.getInstance(this).pitchDao().getPitchId(order.getPitchId()).get(0);
            categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO().getCategoryPitchWithId(pitch.getCategoryId()).get(0);
            customer.setCoin(customer.getCoin() + order.getTotal());

            datePlay = order.getDatePlay();
            dateCreate = order.getDateCreate();
            totalMoneyPitch = order.getTotalPitchMoney();
            chiPhiKhac = order.getChiPhiKhac();
            changeChiPhiKhac();

            listService = (List<ServiceBall>) getIntent().getBundleExtra("bundle")
                    .getSerializable("LIST_SERVICE");
            numberOfService = (List<Integer>) getIntent().getBundleExtra("bundle")
                    .getSerializable("LIST_NUMBER");

            showLichHoatDong();
            setUpTvMocTg("", type_cancel);

            btnDatSan.setText("Update");
            tvSoluotCapNhat.setText(order.getSoLanCapNhat() + "");
        } else {
            tvSoluotCapNhat.setVisibility(View.INVISIBLE);
            tvShow.setVisibility(View.INVISIBLE);
            setOnClickForImageView();
            pitch = (Pitch) getIntent().getSerializableExtra("PITCH");
            categoryPitch = MyDatabase.getInstance(this).pitchCategoryDAO()
                    .getCategoryPitchWithId(pitch.getCategoryId()).get(0);

            Calendar calendar = Calendar.getInstance();
            datePlay = getStringDate(calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            setUpTvDate();

            showLichHoatDong();
        }

        setUpTvDate();
        setUpTvMoneyAndTvPitch();
        changeChiPhiKhac();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_SERVICE == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                listService = (List<ServiceBall>) data.getBundleExtra("bundle").getSerializable("LIST_SERVICE");
                numberOfService = (List<Integer>) data.getBundleExtra("bundle").getSerializable("LIST_NUMBER");
            }
        }
    }

    public void addImageView() {
        imgSelect1 = findViewById(R.id.img_select_san1_user);
        listSelect.add(imgSelect1);
        imgSelect2 = findViewById(R.id.img_select_san2_user);
        listSelect.add(imgSelect2);
        imgSelect3 = findViewById(R.id.img_select_san3_user);
        listSelect.add(imgSelect3);
        imgSelect4 = findViewById(R.id.img_select_san4_user);
        listSelect.add(imgSelect4);
        imgSelect5 = findViewById(R.id.img_select_san5_user);
        listSelect.add(imgSelect5);
        imgSelect6 = findViewById(R.id.img_select_san6_user);
        listSelect.add(imgSelect6);
        imgSelect7 = findViewById(R.id.img_select_san7_user);
        listSelect.add(imgSelect7);
        imgSelect8 = findViewById(R.id.img_select_san8_user);
        listSelect.add(imgSelect8);
        imgSelect9 = findViewById(R.id.img_select_san9_user);
        listSelect.add(imgSelect9);
        imgSelect10 = findViewById(R.id.img_select_san10_user);
        listSelect.add(imgSelect10);
        imgSelect11 = findViewById(R.id.img_select_san11_user);
        listSelect.add(imgSelect11);
        imgSelect12 = findViewById(R.id.img_select_san12_user);
        listSelect.add(imgSelect12);
    }

    public void setUpImageSelect() {
        addImageView();
    }

    public void datSan() {


        if (count <= 0) {
            Toast.makeText(this, "You need to choose at least 1 shift ", Toast.LENGTH_SHORT).show();
        } else {
            int total = totalMoneyPitch + totalMoneyService + chiPhiKhac;
            if (!isUpdate) {

                if (CHECK_OUT == false) {
                    setCHECK_OUTTrue();
                    DialogFragment dialogFragment = paymentContext.processPayMent(paymentstrategy);
                    dialogFragment.show(getSupportFragmentManager(),"");
                    if(CHECK_OUT == true){
                        order = new Order();
                        order.setId(++MainActivity.ID_MAX_ORDER);
                        order.setManagerId(MyDatabase.getInstance(this).managerDAO().getManagerWithPhone("Admin",-1).get(0).getId());
                        Calendar calendar = Calendar.getInstance();
                        dateCreate = getStringDate(calendar.get(Calendar.DATE),
                                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                        order.setDateCreate(dateCreate);
                        order.setPitchId(pitch.getId());
                        order.setCustomerId(customer.getId());
                        order.setTotalPitchMoney(totalMoneyPitch);
                        order.setDatePlay(datePlay);
                        order.setChiPhiKhac(chiPhiKhac);
                        order.setTotalServiceMoney(totalMoneyService);
                        order.setTotal(total);
                        order.setStatus(MyApplication.CHUA_STATUS);
                        order.setSoCa(count);


                        MyDatabase.getInstance(this).orderDAO().insert(order);

                        for (int i = 0; i < typeSelect.length; i++) {
                            if (typeSelect[i] == type_cancel) {
                                TimeOrderDetails timeOrderDetails = new TimeOrderDetails();
                                timeOrderDetails.setTimeId(i + 1);
                                timeOrderDetails.setOrderId(order.getId());
                                MyDatabase.getInstance(this).timeOrderDetailsDAO().insert(timeOrderDetails);
                            }
                        }

                        for (int i = 0; i < listService.size(); i++) {
                            OrderDetails orderDetails = new OrderDetails();
                            orderDetails.setServiceId(listService.get(i).getId());
                            orderDetails.setOrderId(order.getId());
                            orderDetails.setSoLuong(numberOfService.get(i));
                            orderDetails.setTongTien(listService.get(i).getMoney() * numberOfService.get(i));

                            MyDatabase.getInstance(this).orderDetailsDAO().insert(orderDetails);
                        }
                        Toast.makeText(this, "Booking success", Toast.LENGTH_SHORT).show();

                        resetData();
                        showLichHoatDong();
                    }

                }
            } else {
                if (order.getSoLanCapNhat() > 0) {
                    if (customer.getCoin() < total) {
                        Toast.makeText(this, "Customer account don't have enough money", Toast.LENGTH_SHORT).show();
                    } else {
                        customer.setCoin(customer.getCoin() - total);
                        MyDatabase.getInstance(this).customerDAO().update(customer);

                        order.setDatePlay(datePlay);
                        order.setTotalPitchMoney(totalMoneyPitch);
                        order.setChiPhiKhac(chiPhiKhac);
                        order.setTotalServiceMoney(totalMoneyService);
                        order.setTotal(total);
                        order.setSoLanCapNhat(order.getSoLanCapNhat() - 1);
                        order.setSoCa(count);

                        MyDatabase.getInstance(this).orderDAO().update(order);

                        MyDatabase.getInstance(this).orderDetailsDAO().deleteWithOrderId(order.getId());
                        for (int i = 0; i < listService.size(); i++) {
                            OrderDetails orderDetails = new OrderDetails();
                            orderDetails.setServiceId(listService.get(i).getId());
                            orderDetails.setOrderId(order.getId());
                            orderDetails.setSoLuong(numberOfService.get(i));
                            orderDetails.setTongTien(listService.get(i).getMoney() * numberOfService.get(i));

                            MyDatabase.getInstance(this).orderDetailsDAO().insert(orderDetails);
                        }

                        int[] types = new int[12];
                        List<TimeOrderDetails> timeOrderDetails = MyDatabase.getInstance(this)
                                .timeOrderDetailsDAO().getTimeOrderWithOrderId(order.getId());
                        for (int i = 0; i < timeOrderDetails.size(); i++) {
                            int timeId = timeOrderDetails.get(i).getTimeId();
                            types[timeId - 1] = 1;
                        }
                        for (int i = 0; i < typeSelect.length; i++) {
                            if (typeSelect[i] == type_cancel && types[i] == 0) {
                                TimeOrderDetails timeOrderDetails1 = new TimeOrderDetails();
                                timeOrderDetails1.setOrderId(order.getId());
                                timeOrderDetails1.setTimeId(i + 1);
                                MyDatabase.getInstance(this).timeOrderDetailsDAO().insert(timeOrderDetails1);
                            } else if (typeSelect[i] == type_add && types[i] == 1) {
                                MyDatabase.getInstance(this).timeOrderDetailsDAO().deleteWithOrderIdAndTimeId(order.getId(), i + 1);
                            }
                        }

                        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "You have used up the number of updates", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openDialog() {
        MoMoDialog moMoDialog = new MoMoDialog();
        moMoDialog.show(getSupportFragmentManager(),"MOMO");
    }

    public void setOnClickForImageView() {
        for (int i = 0; i < listSelect.size(); i++) {
            int finalI = i;
            listSelect.get(i).setOnClickListener(v -> {
                if (typeSelect[finalI] == type_add && count < maxCount) {
                    count += 1;
                    typeSelect[finalI] = type_cancel;
                    setImageResourceAtPos(finalI);
                    setUpTvMocTg("Shift" + (finalI + 1), type_add);

                    MyTime time = MyDatabase.getInstance(this).timeDAO().getTimeWithId(finalI + 1).get(0);
                    chiPhiKhac += time.getMoney();
                    changeChiPhiKhac();
                    changeTotalMoneyPitch(categoryPitch.getMoney() * 2);
                } else if (typeSelect[finalI] == type_cancel) {
                    count -= 1;
                    typeSelect[finalI] = type_add;
                    setImageResourceAtPos(finalI);
                    setUpTvMocTg("", type_cancel);

                    MyTime time = MyDatabase.getInstance(this).timeDAO().getTimeWithId(finalI + 1).get(0);
                    chiPhiKhac -= time.getMoney();
                    changeChiPhiKhac();
                    changeTotalMoneyPitch(-categoryPitch.getMoney() * 2);
                } else if (typeSelect[finalI] == type_cancel_gray) {
                    Toast.makeText(this, "You can't cancel a kicked timeline", Toast.LENGTH_SHORT).show();
                } else if (typeSelect[finalI] == type_addGray) {
                    Toast.makeText(this, "Timeline has expired", Toast.LENGTH_SHORT).show();
                } else if (typeSelect[finalI] == type_full) {
                    Toast.makeText(this, "Timeline has been booked by customers", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setImageResourceAtPos(int pos) {
        if (typeSelect[pos] == type_add) {
            listSelect.get(pos).setImageResource(R.drawable.ic_add);
        } else if (typeSelect[pos] == type_cancel) {
            listSelect.get(pos).setImageResource(R.drawable.ic_cancel);
        }
    }

    public void changeTotalMoneyPitch(int money) {
        totalMoneyPitch += money;
        setUpTvMoneyAndTvPitch();
    }

    public void changeChiPhiKhac() {
        tvChiPhi.setText(MyApplication.convertMoneyToString(chiPhiKhac) + " VNĐ");
        setUpTotalMoney();
    }

    public void setUpTvMocTg(String s, int type) {
        if (type == type_add) {
            if (tvMocTg.getText().toString().equals("")) {
                tvMocTg.setText(s);
            } else {
                tvMocTg.setText(tvMocTg.getText().toString() + "-" + s);
            }
        } else {
            tvMocTg.setText("");
            for (int i = 0; i < typeSelect.length; i++) {
                if (typeSelect[i] == type_cancel || typeSelect[i] == type_cancel_gray) {
                    setUpTvMocTg("Shift" + (i + 1), type_add);
                }
            }
        }
    }

    public void setOnCLickForView() {
        tvDate.setOnClickListener(v -> {
            if (!isUpdate) {
                if(count>0){
                    Toast.makeText(UserDatSanChiTietActivity.this, "You need to cancel the calendar of the day " + datePlay, Toast.LENGTH_SHORT).show();
                }else {
                    openDateDialogTvDate();
                }
            }
        });
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
        btnDatSan.setOnClickListener(v -> {
            datSan();
        });
        tvShowCaThiDau.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowCaThiDauActivity.class);
            startActivity(intent);
            Animatoo.INSTANCE.animateSlideLeft(this);
        });
    }

    public void setUpTvMoneyAndTvPitch() {
        tv_tensan.setText(pitch.getName());
        tvSanBongMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch) + " VNĐ");
        setUpTotalMoney();
    }

    public void resetData() {
        count = 0;
        totalMoneyPitch = 0;
        order = null;
        chiPhiKhac = 0;
        listService = new ArrayList<>();
        numberOfService = new ArrayList<>();
        setUpTvMoneyAndTvPitch();
        changeChiPhiKhac();
        tvMocTg.setText("");
    }

    public void showLichHoatDong() {
        resetTypeSelect();

        Calendar calendar = Calendar.getInstance();
        String s = getStringDate(calendar.get(Calendar.DATE),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

        // kiểm tra thời gian quá khứ
        if (s.equals(datePlay)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            for (int i = 0; i < 12; i++) {
                if (hour >= i * 2) {
                    typeSelect[i] = type_addGray;
                }
            }
        } else if (checkDate(s)) {
            for (int i = 0; i < 12; i++) {
                typeSelect[i] = type_addGray;
            }
        }
        // Kiểm tra thời gian bị full
        List<TimeOrderDetails> timeOrderDetails;
        if(order != null){
            if(order.getStatus() == MyApplication.HUY_STATUS){
                timeOrderDetails =
                        MyDatabase.getInstance(this).timeOrderDetailsDAO().getTimeOrderWithOrderId(order.getId());
            }else{
                timeOrderDetails =
                        MyDatabase.getInstance(this).timeOrderDetailsDAO().getTimeOrderWithDateAndPitch(datePlay, pitch.getId(),MyApplication.HUY_STATUS);
            }
        }else{
            timeOrderDetails =
                    MyDatabase.getInstance(this).timeOrderDetailsDAO().getTimeOrderWithDateAndPitch(datePlay, pitch.getId(),MyApplication.HUY_STATUS);
        }

        for (int i = 0; i < timeOrderDetails.size(); i++) {
            int idTime = timeOrderDetails.get(i).getTimeId();
            if (order != null) {
                if(count != 0) {
                    if (order.getId() == timeOrderDetails.get(i).getOrderId()) {
                        if (typeSelect[idTime - 1] == type_addGray) {
                            typeSelect[idTime - 1] = type_cancel_gray;
                        } else {
                            typeSelect[idTime - 1] = type_cancel;
                        }
                    } else {
                        typeSelect[idTime - 1] = type_full;
                    }
                }
            } else {
                typeSelect[idTime - 1] = type_full;
            }
        }

        setResourceForImageSelect();
    }

    public boolean checkDate(String s) {

        int[] arr1 = getArrayDate(s);
        int[] arr2 = getArrayDate(datePlay);

        if (arr2[2] < arr1[2]) {
            return true;
        } else if (arr2[1] < arr1[1]) {
            return true;
        } else return arr2[0] < arr1[0];
    }

    public void resetTypeSelect() {
        Arrays.fill(typeSelect, 0);
    }

    public void setResourceForImageSelect() {
        for (int i = 0; i < listSelect.size(); i++) {
            if (typeSelect[i] == type_add) {
                listSelect.get(i).setImageResource(R.drawable.ic_add);
            } else if (typeSelect[i] == type_full) {
                listSelect.get(i).setImageResource(R.drawable.soldout_png);
            } else if (typeSelect[i] == type_addGray) {
                listSelect.get(i).setImageResource(R.drawable.ic_add_gray);
            } else if (typeSelect[i] == type_cancel) {
                listSelect.get(i).setImageResource(R.drawable.ic_cancel);
            } else if (typeSelect[i] == type_cancel_gray) {
                listSelect.get(i).setImageResource(R.drawable.ic_cancel_gray);
            }
        }
    }

    public void openDateDialogTvDate() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DATE);
        DatePickerDialog pickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog, (view, year, month, dayOfMonth) -> {
            if (year < mYear) {
                Toast.makeText(this,
                        "Can't choose the date of the past", Toast.LENGTH_SHORT).show();
            } else if (year == mYear) {
                if (month < mMonth) {
                    Toast.makeText(this,
                            "Can't choose the date of the past", Toast.LENGTH_SHORT).show();
                } else if (month == mMonth) {
                    if (dayOfMonth < mDate) {
                        Toast.makeText(this,
                                "Can't choose the date of the past", Toast.LENGTH_SHORT).show();
                    } else {
                        String dateChose = getStringDate(dayOfMonth, month + 1, year);
                        if (!dateChose.equals(datePlay)) {
                            datePlay = dateChose;
                            setUpTvDate();
                            showLichHoatDong();
                        }
                    }
                } else {
                    String dateChose = getStringDate(dayOfMonth, month + 1, year);
                    if (!dateChose.equals(datePlay)) {
                        datePlay = dateChose;
                        setUpTvDate();
                        showLichHoatDong();
                    }
                }
            } else {
                String dateChose = getStringDate(dayOfMonth, month + 1, year);
                if (!dateChose.equals(datePlay)) {
                    datePlay = dateChose;
                    setUpTvDate();
                    showLichHoatDong();
                }
            }
        }, mYear, mMonth, mDate);

        pickerDialog.setCancelable(false);
        pickerDialog.setTitle("Choose day : ");
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    public void setUpTvDate() {
        tvDate.setText(getThu());
    }

    public void setUpTotalMoney() {
        tvAllMoney.setText(MyApplication.convertMoneyToString(totalMoneyPitch + totalMoneyService + chiPhiKhac) + " VNĐ");
    }



    public String getStringDate(int d, int m, int y) {
        return d + "-" + m + "-" + y;
    }

    public int[] getArrayDate(String s) {
        String[] str = s.split("-");
        int arr[] = new int[str.length];
        try {
            for (int i = 0; i < str.length; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return arr;
    }

    public String getThu() {
        int[] arr = getArrayDate(datePlay);
        Calendar calendar = Calendar.getInstance();
        if (arr != null) calendar.set(arr[2], arr[1] - 1, arr[0]);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "Sun " + datePlay;
            case 2:
                return "Mon " + datePlay;
            case 3:
                return "Tue " + datePlay;
            case 4:
                return "Wed " + datePlay;
            case 5:
                return "Thur " + datePlay;
            case 6:
                return "Fri " + datePlay;
            case 7:
                return "Sat " + datePlay;
            default:
                return "";
        }
    }

    public void initView() {
        tv_tensan = findViewById(R.id.tv_name_user_datsanchitiet);
        tvDate = findViewById(R.id.tv_date_user_datsanchitiet);
//        tvService = findViewById(R.id.tv_service_user_datsanchitiet);
//        tvServiceMoney = findViewById(R.id.tv_money_service_user_datsanchitiet);
        tvSanBongMoney = findViewById(R.id.tv_money_san_user_datsanchitiet);
        tvAllMoney = findViewById(R.id.tv_tongtien_user_datsanchitiet);
        btnDatSan = findViewById(R.id.btn_datsan_user_datsanchitiet);
//        btnServiceDetails = findViewById(R.id.btn_chitiet_user_datsanchitiet);
        imgBack = findViewById(R.id.btn_back_user_datsanchitiet);
        tvMocTg = findViewById(R.id.tv_moctg_user_datsanchitiet);
        tvShowCaThiDau = findViewById(R.id.tv_caTime_user_datsanchitiet);
        tvChiPhi = findViewById(R.id.tv_chiphi_user_datsanchitiet);
        tvSoluotCapNhat = findViewById(R.id.tv_soluotcapnhat);
        tvShow = findViewById(R.id.tv_show);
    }

    public boolean isCHECK_OUT() {
        return CHECK_OUT;
    }

    public void setCHECK_OUTFalse() {
        CHECK_OUT = false;
    }
    public static void setCHECK_OUTTrue() {
        CHECK_OUT = true;
    }

}

