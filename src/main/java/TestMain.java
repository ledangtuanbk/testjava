import com.squareup.okhttp.*;
import org.json.JSONObject;

import java.io.*;

public class TestMain {
    private static final String LOGIN = "http://localhost:47503/api/token/take";
    private static final String SEND_FILE = "http://localhost:47503/api/egw/guiHoSoGiamDinh4210";

//    private static final String LOGIN = "http://egw.baohiemxahoi.gov.vn/api/token/take";
//    private static final String SEND_FILE = "http://egw.baohiemxahoi.gov.vn/api/egw/guiHoSoGiamDinh4210";

    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String API_KEY = "APIKey";
    private static final String ID_TOKEN = "id_token";
    private static final String TOKEN = "token";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String LOAI_HO_SO = "loaiHoSo";
    private static final String MA_TINH = "maTinh";
    private static final String MA_CSKCB = "maCSKCB";
    private static final String HN = "01";
    private static final String BVE = "01007";

    static String userNameE = "01007_BV";
    static String passwordE = "17163719f3c3ccce1b0e78b31de293e6";

        static String filePath = "C:\\\\All.xml";
//    static String filePath = "/home/ldt/Desktop/DataE.xml";

    public static void main(String[] args) {
        try {
            String result = guiHoSo();
            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();

    static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        return response.body().string();
    }


    static String login() {
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(USER_NAME, userNameE);
            jsonObject.put(PASSWORD, passwordE);
            result = post(LOGIN, jsonObject.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    static String guiHoSo() {

        String loginResult = login();
        JSONObject jsonObject = new JSONObject(loginResult);
        JSONObject keyObj = jsonObject.getJSONObject(API_KEY);

        String token = keyObj.getString(ACCESS_TOKEN);
        String idToken = keyObj.getString(ID_TOKEN);
        String result = "";
        try {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(SEND_FILE).newBuilder();
            urlBuilder.addQueryParameter(TOKEN, token);
            urlBuilder.addQueryParameter(ID_TOKEN, idToken);
            urlBuilder.addQueryParameter(USER_NAME, userNameE);
            urlBuilder.addQueryParameter(PASSWORD, passwordE);
            urlBuilder.addQueryParameter(LOAI_HO_SO, "3");
            urlBuilder.addQueryParameter(MA_TINH, HN);
            urlBuilder.addQueryParameter(MA_CSKCB, BVE);
            String url = urlBuilder.build().toString();
            System.out.printf("url " + url);

            File file = new File(filePath);
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();


            RequestBody requestBody = RequestBody.create(JSON, bytesArray);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            result = response.body().string();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }
}
