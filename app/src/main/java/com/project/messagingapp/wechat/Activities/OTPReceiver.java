package com.project.messagingapp.wechat.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.mukesh.OtpView;

public class OTPReceiver extends BroadcastReceiver
{
    private static OtpView editText;

    public void setEditText(OtpView editText)
    {
        OTPReceiver.editText = editText;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage sms : messages)
        {
            String message = sms.getMessageBody();
            int otp = Integer.parseInt(message);
            editText.setText(otp);

        }
    }

}
