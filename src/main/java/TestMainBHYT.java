import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestMainBHYT {
//    private static final String LOGIN = "http://localhost:47503/api/token/take";
//    private static final String SEND_FILE = "http://localhost:47503/api/egw/guiHoSoGiamDinh4210";

    private static final String LOGIN = "https://egw.baohiemxahoi.gov.vn/api/token/take";
    private static final String SEND_FILE = "https://egw.baohiemxahoi.gov.vn/api/egw/SendFileHoSoGiamDinh_zip4210";

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
    private static final String BVE = "01043";

    static String userNameE = "01043_BV";
    static String passwordE = "3d99e45b911d7ce222ddc2c2e858d40f";

    static String filePath = "/home/ldt/All198.xml";

    public static void main(String[] args) {
        try {
            TestMainBHYT testMainBHYT = new TestMainBHYT();
            String result = testMainBHYT.guiHoSo();
            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String getBody() {
        File file = new File(filePath);
        byte[] bytes = readFileToByteArray(file);
        String base64String = Base64.encodeBase64String(bytes);
        return base64String;
    }


    private static byte[] readFileToByteArray(File file) {
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        return bArray;
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

    String guiHoSo() {

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

            RequestBody formBody = new FormBody.Builder().add("FileHoSoJson","\"" + getBody() + "\"").build();
            final Request request = new Request.Builder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            result = response.body().string();
            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }


}
