package com.example.foodapp.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.foodapp.Model.Feedback;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Model.OrderInfo;
import com.example.foodapp.Model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DatabaseName = "FoodApp.db";
    private static final int DatabaseVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'taikhoan' TEXT UNIQUE not null, 'matkhau' TEXT not null, 'diaChi' TEXT, 'hoTen' TEXT, 'sdt' TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS DoAn ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'tendoan' TEXT, 'giadoan' FLOAT, 'giakhuyenmai' FLOAT, 'motadoan' TEXT, 'anhdoan' INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS OrderFood ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'userId' INTEGER, 'trangthai' TEXT, 'ngaydat' TEXT, 'giodat' TEXT, 'tongtien' FLOAT, FOREIGN KEY('userId') REFERENCES User('id'))");

        db.execSQL("CREATE TABLE IF NOT EXISTS OrderDetail ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'orderId' INTEGER,'foodId' INTEGER, 'soluong' INTEGER, 'thanhtien' FLOAT, FOREIGN KEY('orderId') REFERENCES OrderFood('id'),  FOREIGN KEY('foodId') REFERENCES DoAn('id'))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Feedback ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'text' TEXT, 'timestamp' DATETIME DEFAULT CURRENT_TIMESTAMP, 'idUser' INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists DoAn");
        db.execSQL("drop table if exists OrderFood");
        db.execSQL("drop table if exists OrderDetail");
        db.execSQL("drop table if exists Feedback");

        onCreate(db);
    }

    public boolean isUserTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM User", null);

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);

            cursor.close();
            db.close();

            return count == 0;
        }

        return true;
    }
    public void insertAcc(String taiKhoan, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taikhoan", taiKhoan);
        values.put("matkhau", matKhau);

        long result = db.insert("User", null, values);
        if (result == -1) {
            Log.d("DatabaseHelper", "Thêm tài khoản không thành công.");
        } else {
            Log.d("DatabaseHelper", "Thêm tài khoản thành công.");
        }
        db.close();
    }

    public boolean isLoginValid(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isValid = false;

        try {
            String query = "SELECT * FROM User WHERE taikhoan=?";
            cursor = db.rawQuery(query, new String[]{username});

            if (cursor != null && cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex("matkhau");

                if (passwordIndex != -1) {
                    String storedPassword = cursor.getString(passwordIndex);

                    if (password.equals(storedPassword)) {
                        isValid = true;
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isValid;
    }




    public void insertDoAn(String tenDoAn, float giaDoAn, float giaKhuyenMai, String moTaDoAn, int anhDoAn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Đặt giá trị cho các cột trong bảng
        values.put("tendoan", tenDoAn);
        values.put("giadoan", giaDoAn);
        values.put("giakhuyenmai", giaKhuyenMai);
        values.put("motadoan", moTaDoAn);
        values.put("anhdoan", anhDoAn);

        // Chèn dữ liệu vào bảng
        long newRowId = db.insert("DoAn", null, values);

        // Kiểm tra xem việc chèn có thành công hay không
        if (newRowId == -1) {
            // Xảy ra lỗi khi chèn dữ liệu
            Log.e("SQLite", "Lỗi khi chèn dữ liệu vào bảng DoAn");
        } else {
            // Thành công
            Log.i("SQLite", "Đã chèn dữ liệu thành công vào bảng DoAn, ID: " + newRowId);
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();
    }

    public ArrayList<Food> getAllDoAn() {
        ArrayList<Food> alldata = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DoAn", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id")); // Retrieve the ID column
                String tenDoAn = cursor.getString(cursor.getColumnIndex("tendoan"));
                float giaDoAn = cursor.getFloat(cursor.getColumnIndex("giadoan"));
                float giaKhuyenMai = cursor.getFloat(cursor.getColumnIndex("giakhuyenmai"));
                String moTaDoAn = cursor.getString(cursor.getColumnIndex("motadoan"));
                int anhDoAn = cursor.getInt(cursor.getColumnIndex("anhdoan"));

                Food food = new Food(id, tenDoAn, giaDoAn, giaKhuyenMai, moTaDoAn, anhDoAn);
                alldata.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return alldata;
    }


    public boolean isDoAnTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM DoAn", null);

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);

            cursor.close();
            db.close();

            return count == 0;
        }

        return true;
    }

    public Food getFoodById(int foodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Food food = null;

        String[] columns = {
                "id",
                "tendoan",
                "giadoan",
                "giakhuyenmai",
                "motadoan",
                "anhdoan"
        };

        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(foodId) };

        Cursor cursor = db.query("DoAn", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tenDoAn = cursor.getString(cursor.getColumnIndex("tendoan"));
                float giaDoAn = cursor.getFloat(cursor.getColumnIndex("giadoan"));
                float giaKhuyenMai = cursor.getFloat(cursor.getColumnIndex("giakhuyenmai"));
                String moTaDoAn = cursor.getString(cursor.getColumnIndex("motadoan"));
                int anhDoAn = cursor.getInt(cursor.getColumnIndex("anhdoan"));

                // Initialize a Food object from the database data
                food = new Food(id, tenDoAn, giaDoAn, giaKhuyenMai, moTaDoAn, anhDoAn);
            }
            cursor.close();
        }

        return food;
    }



    public long updateOrInsertCartItem(int userId, String status, int foodId, int quantity, String ngaydat, String giodat, float thanhtien) {
        SQLiteDatabase db = this.getWritableDatabase();
        long orderFoodId = -1; // Khởi tạo một giá trị mặc định

        // Trước tiên, kiểm tra xem foodId đã tồn tại trong giỏ hàng của userId và status hay chưa
        String query = "SELECT OrderFood.id FROM OrderFood " +
                "WHERE userId = ? AND trangthai = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), status});

        if (cursor.moveToFirst()) {
            // Đã tồn tại giỏ hàng, lấy ID của giỏ hàng
            orderFoodId = cursor.getLong(cursor.getColumnIndex("id"));
        } else {
            // Chưa tồn tại, tạo mới
            ContentValues orderFoodValues = new ContentValues();
            orderFoodValues.put("userId", userId);
            orderFoodValues.put("trangthai", status);
            orderFoodValues.put("ngaydat", ngaydat);
            orderFoodValues.put("giodat", giodat);

            orderFoodId = db.insert("OrderFood", null, orderFoodValues);
        }

        // Kiểm tra và thêm chi tiết đặt hàng vào OrderDetail
        if (orderFoodId != -1) {
            // Check if the food item already exists in OrderDetail
            query = "SELECT * FROM OrderDetail WHERE orderId = ? AND foodId = ?";
            Cursor detailCursor = db.rawQuery(query, new String[]{String.valueOf(orderFoodId), String.valueOf(foodId)});

            if (detailCursor.moveToFirst()) {
                // Food item already exists, update the quantity and total cost
                int existingQuantity = detailCursor.getInt(detailCursor.getColumnIndex("soluong"));
                int newQuantity = existingQuantity + quantity;
                float newTotalCost = newQuantity * thanhtien;

                ContentValues updateValues = new ContentValues();
                updateValues.put("soluong", newQuantity);
                updateValues.put("thanhtien", newTotalCost);

                // Update the existing entry
                db.update("OrderDetail", updateValues, "orderId = ? AND foodId = ?",
                        new String[]{String.valueOf(orderFoodId), String.valueOf(foodId)});
            } else {
                // Food item doesn't exist, add a new entry
                ContentValues orderDetailValues = new ContentValues();
                orderDetailValues.put("orderId", orderFoodId);
                orderDetailValues.put("foodId", foodId);
                orderDetailValues.put("soluong", quantity);
                orderDetailValues.put("thanhtien", thanhtien); // Cập nhật tổng tiền

                db.insert("OrderDetail", null, orderDetailValues);
            }

            detailCursor.close();
        }

        cursor.close();
        return orderFoodId; // Trả về ID của giỏ hàng đã tạo hoặc cập nhật
    }




    public boolean updateOrderStatusAndTotalPrice(int userId, String newStatus, float newTotalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangthai", newStatus);
        values.put("tongtien", newTotalPrice);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        values.put("ngaydat", dateFormat.format(currentDate));
        values.put("giodat", timeFormat.format(currentDate));

        int rowsUpdated = db.update("OrderFood", values, "userId = ? AND trangthai = ?", new String[]{String.valueOf(userId), "giohang"});
        db.close();

        return rowsUpdated > 0;
    }








    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Giá trị mặc định nếu không tìm thấy

        String query = "SELECT id FROM User WHERE taikhoan = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0); // Lấy giá trị ID từ cột đầu tiên
        }

        cursor.close();
        return userId;
    }

    public ArrayList<Food> getCartDoAnByUser(int userId) {
        ArrayList<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DoAn.id, DoAn.tendoan, DoAn.giadoan, DoAn.giakhuyenmai, DoAn.motadoan, DoAn.anhdoan, OrderDetail.soluong " +
                "FROM DoAn INNER JOIN OrderDetail ON DoAn.id = OrderDetail.foodId " +
                "INNER JOIN OrderFood ON OrderDetail.orderId = OrderFood.id " +
                "WHERE OrderFood.userId = ? AND OrderFood.trangthai = 'giohang'", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tenDoAn = cursor.getString(cursor.getColumnIndex("tendoan"));
                float giaDoAn = cursor.getFloat(cursor.getColumnIndex("giadoan"));
                float giaKhuyenMai = cursor.getFloat(cursor.getColumnIndex("giakhuyenmai"));
                String moTaDoAn = cursor.getString(cursor.getColumnIndex("motadoan"));
                int anhDoAn = cursor.getInt(cursor.getColumnIndex("anhdoan"));
                int soLuong = cursor.getInt(cursor.getColumnIndex("soluong"));

                Food food = new Food(id, tenDoAn, giaDoAn, giaKhuyenMai, moTaDoAn, anhDoAn);
                food.setSoLuong(soLuong);

                foodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodList;
    }


    public void updateCartItemQuantity(int orderId, int foodId, int newQuantity, float newTotalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong", newQuantity);
        values.put("thanhtien", newTotalPrice); // Update the total price as well

        db.update("OrderDetail", values, "orderId = ? AND foodId = ?", new String[]{String.valueOf(orderId), String.valueOf(foodId)});

        db.close();
    }



    public boolean deleteCartItem(int orderId, int foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[]{String.valueOf(orderId), String.valueOf(foodId)};

        int rowsDeleted = db.delete("OrderDetail", "orderId = ? AND foodId = ?", whereArgs);

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        // Kiểm tra xem có hàng nào bị xóa không (rowsDeleted > 0)
        boolean isDeleted = rowsDeleted > 0;

        Log.d("DeleteCartItem", "Deleted " + rowsDeleted + " rows for orderId: " + orderId + ", foodId: " + foodId);
        return isDeleted;
    }



    public User getUserById(int userId) {
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id", "taikhoan", "matkhau", "diaChi", "hoTen", "sdt"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String taikhoan = cursor.getString(cursor.getColumnIndex("taikhoan"));
            String matkhau = cursor.getString(cursor.getColumnIndex("matkhau"));
            String diaChi = cursor.getString(cursor.getColumnIndex("diaChi"));
            String hoTen = cursor.getString(cursor.getColumnIndex("hoTen"));
            String sdt = cursor.getString(cursor.getColumnIndex("sdt"));

            user = new User(id, taikhoan, matkhau, diaChi, hoTen, sdt);
        }

        cursor.close();
        db.close();

        return user;
    }


    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {"id", "taikhoan", "matkhau", "diaChi", "hoTen", "sdt"};
        String selection = "taikhoan = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("User", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String taikhoan = cursor.getString(cursor.getColumnIndex("taikhoan"));
            String matkhau = cursor.getString(cursor.getColumnIndex("matkhau"));
            String diaChi = cursor.getString(cursor.getColumnIndex("diaChi"));
            String hoTen = cursor.getString(cursor.getColumnIndex("hoTen"));
            String sdt = cursor.getString(cursor.getColumnIndex("sdt"));

            user = new User(id, taikhoan, matkhau, diaChi, hoTen, sdt);
        }

        cursor.close();
        db.close();

        return user;
    }


    public boolean updateUser(String username, String diaChi, String hoTen, String sdt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("diaChi", diaChi);
        values.put("hoTen", hoTen);
        values.put("sdt", sdt);

        String selection = "taikhoan = ?";
        String[] selectionArgs = {username};

        int rowsUpdated = db.update("User", values, selection, selectionArgs);

        db.close();

        return rowsUpdated > 0; // Trả về true nếu có ít nhất một hàng đã được cập nhật
    }

    public List<OrderInfo> getOrdersWithUserInfo(int userId, String status) {
        List<OrderInfo> orderList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Thực hiện truy vấn để lấy thông tin đơn hàng và kết hợp với thông tin người dùng
        String query = "SELECT OrderFood.id, trangthai, ngaydat, giodat, tongtien, " +
                "User.hoTen, User.diaChi, User.sdt, " +
                "GROUP_CONCAT('- ' || REPLACE(DoAn.tendoan, ',', '') || ' x' || OrderDetail.soluong || '\n') AS foodDetails, " +
                "SUM(OrderDetail.thanhtien) AS orderTotal " +
                "FROM OrderFood " +
                "INNER JOIN User ON OrderFood.userId = User.id " +
                "INNER JOIN OrderDetail ON OrderFood.id = OrderDetail.orderId " +
                "INNER JOIN DoAn ON OrderDetail.foodId = DoAn.id " +
                "WHERE OrderFood.userId = ? AND OrderFood.trangthai = ? " +
                "GROUP BY OrderFood.id";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), status});

        while (cursor.moveToNext()) {
            int orderId = cursor.getInt(cursor.getColumnIndex("id"));
            String orderStatus = cursor.getString(cursor.getColumnIndex("trangthai"));
            String orderDate = cursor.getString(cursor.getColumnIndex("ngaydat"));
            String orderTime = cursor.getString(cursor.getColumnIndex("giodat"));
            float totalAmount = cursor.getFloat(cursor.getColumnIndex("tongtien"));
            String userName = cursor.getString(cursor.getColumnIndex("hoTen"));
            String userAddress = cursor.getString(cursor.getColumnIndex("diaChi"));
            String userPhoneNumber = cursor.getString(cursor.getColumnIndex("sdt"));
            String foodDetails = cursor.getString(cursor.getColumnIndex("foodDetails"));
            float orderTotal = cursor.getFloat(cursor.getColumnIndex("orderTotal"));

            OrderInfo orderInfo = new OrderInfo(orderId, orderStatus, orderDate, orderTime, totalAmount,
                    userName, userAddress, userPhoneNumber, foodDetails, orderTotal);
            orderList.add(orderInfo);
        }

        cursor.close();
        db.close();

        return orderList;
    }

    public long addFeedback(String text, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", text);
        values.put("idUser", userId);

        long newRowId = db.insert("Feedback", null, values);
        db.close();
        return newRowId;
    }



}
