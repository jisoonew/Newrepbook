package com.navercorp.nid.oauth.plugin.legacy;

import android.content.UriMatcher;
import android.net.Uri;

import com.navercorp.nid.log.NidLog;

import java.net.MalformedURLException;
import java.net.URL;

public class WebLoadUtil {

    private static final String TAG = "WebLoadUtil";

    private static final int EMPTY_URL = 10;
    private static final int BLANK_URL = 11;

    private static final int NID_DOMAIN = 20;
    private static final int NID_DOMAIN_ID = 21;
    private static final int NID_DOMAIN_PW = 22;
    private static final int NID_DOMAIN_JOIN = 23;
    private static final int NID_DOMAIN_LOGOUT = 24;

    private static final int SSO_LOGOUT = 30;
    private static final int SSO_CROSS_DOMAIN = 31;
    private static final int SSO_FINALIZE = 32;

    private static final int CC_NAVER = 40;
    private static final int CR_NAVER = 41;

    private static final int VNO = 50;
    private static final int OK_NAME = 51;
    private static final int SIREN = 52;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("nid.naver.com", "mobile/user/help/idInquiry.nhn", NID_DOMAIN_ID);
        uriMatcher.addURI("nid.naver.com", "mobile/user/help/pwInquiry.nhn", NID_DOMAIN_PW);
        uriMatcher.addURI("nid.naver.com", "user2/V2Join.nhn", NID_DOMAIN_JOIN);
        uriMatcher.addURI("nid.naver.com", "nidlogin.logout", NID_DOMAIN_LOGOUT);

        uriMatcher.addURI("*", "/sso/logout.nhn", SSO_LOGOUT);
        uriMatcher.addURI("*", "/sso/cross-domain.nhn", SSO_CROSS_DOMAIN);
        uriMatcher.addURI("*", "/sso/finalize.nhn", SSO_FINALIZE);

        uriMatcher.addURI("cc.naver.com", "*", CC_NAVER);
        uriMatcher.addURI("cr.naver.com", "*", CR_NAVER);

        uriMatcher.addURI("cert.vno.co.kr", "*", VNO);
        uriMatcher.addURI("ipin.ok-name.co.kr", "*", OK_NAME);
        uriMatcher.addURI("ipin.siren24.com", "*", SIREN);
    }


    public static boolean isInAppBrowserUrl(String url) {
        if (url.length() <= 0 || url.contentEquals("about:blank")) {
            return true;
        }

        Uri uri = Uri.parse(url);
        int match = uriMatcher.match(uri);

        switch (match) {
            case NID_DOMAIN_ID:
            case NID_DOMAIN_PW:
            case NID_DOMAIN_JOIN:
                return false;
//            case NID_DOMAIN:
//            case NID_DOMAIN_LOGOUT:
//            case SSO_LOGOUT:
//            case SSO_CROSS_DOMAIN:
//            case SSO_FINALIZE:
//            case CC_NAVER:
//            case CR_NAVER:
//            case VNO:
//            case OK_NAME:
//            case SIREN:
//                return true;
        }
        return true;
    }

    public static boolean loadBrowser(String urlString) {
        if (urlString.length() <= 0 || urlString.contentEquals("about:blank")) {
            return false;
        }

        // SSO ?????? ??????
        if (urlString.contains("/sso/logout.nhn") || urlString.contains("/sso/cross-domain.nhn") || urlString.contains("/sso/finalize.nhn")) {
            return false;
        }

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            NidLog.e(TAG, e);
        }

        String host = url.getHost();

        // ???????????????????????? ????????????
        if (host.equals("ipin.siren24.com")) {
            return false;
        }
        // ????????? ??????????????? ????????????
        else if (host.equals("ipin.ok-name.co.kr")) {
            return false;
        }
        // ????????? ???????????? ????????????
        else if (host.equals("cert.vno.co.kr")) {
            return false;
        }
        // cc.naver.com || cr.naver.com
        else if (host.equals("cc.naver.com") || host.equals("cr.naver.com")) {
            return false;
        }
        // nid.naver.com
        else if (host.equals("nid.naver.com")) {
            String path = url.getPath();

            // logout
            if (path.startsWith("/nidlogin.logout")) {
                return false;
            }
            // ????????? ??????, ???????????? ??????, ??????????????? ?????????
            else if (path.startsWith("/mobile/user/help/idInquiry.nhn") || path.startsWith("/mobile/user/help/pwInquiry.nhn") || path.startsWith("/user/mobile_join.nhn")) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean improveLoadBrowser(String urlString) {
        if (urlString.length() <= 0 || urlString.contentEquals("about:blank")) {
            return false;
        }

        // SSO ?????? ??????
        if (urlString.contains("/sso/logout.nhn") || urlString.contains("/sso/cross-domain.nhn") || urlString.contains("/sso/finalize.nhn")) {
            return false;
        }



        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            NidLog.e(TAG, e);
        }

        String host = url.getHost();

        // ???????????????????????? ????????????
        if (host.equals("ipin.siren24.com")) {
            return false;
        }
        // ????????? ??????????????? ????????????
        else if (host.equals("ipin.ok-name.co.kr")) {
            return false;
        }
        // ????????? ???????????? ????????????
        else if (host.equals("cert.vno.co.kr")) {
            return false;
        }
        // cc.naver.com || cr.naver.com
        else if (host.equals("cc.naver.com") || host.equals("cr.naver.com")) {
            return false;
        }
        // nid.naver.com
        else if (host.equals("nid.naver.com")) {
            String path = url.getPath();

            // logout
            if (path.startsWith("/nidlogin.logout")) {
                return false;
            }
            // ????????? ??????, ???????????? ??????, ??????????????? ?????????
            else if (path.startsWith("/mobile/user/help/idInquiry.nhn") || path.startsWith("/mobile/user/help/pwInquiry.nhn") || path.startsWith("/user/mobile_join.nhn")) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

}
