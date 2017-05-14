package com.albaradocompany.jose.proyect_meme_clean.global.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;

/**
 * Created by jose on 12/05/2017.
 */

public class ActivityHelper {
    public static void launchBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void launchEmailSelector(Context context, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void launchWhatsapp(Context context, String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        context.startActivity(Intent.createChooser(i, ""));
    }

    public static void launchFacebook(Context context, String facebookUsername) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(BuildConfig.FACEBOOK_PAGE + facebookUsername));
        context.startActivity(intent);
    }

    public static void launchInstagram(Context context, String instagramUsername) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(BuildConfig.INSTAGRAM_PAGE + instagramUsername));
        context.startActivity(intent);
    }

    public static void launchTwitter(Context context, String twitterUsername) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(BuildConfig.TWITTER_PAGE + twitterUsername));
        context.startActivity(intent);
    }
}
