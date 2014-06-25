package SMS;

import android.telephony.SmsManager;


public class sendSMS {
	//private String msgTxt,numTxt;
	
	public sendSMS(String numTxt,String msgTxt){
		 SmsManager sms=SmsManager.getDefault();
		 sms.sendTextMessage(numTxt, null, msgTxt, null, null);
	}
}
