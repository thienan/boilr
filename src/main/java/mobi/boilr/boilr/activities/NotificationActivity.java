package mobi.boilr.boilr.activities;

import mobi.boilr.boilr.R;
import mobi.boilr.boilr.services.NotificationService;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Alarm activity that pops up a visible indicator when the alarm goes off.
 */
public class NotificationActivity extends Activity {

	private int alarmID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alarmID = getIntent().getIntExtra("alarmID", Integer.MIN_VALUE);
		String firingReason = getIntent().getStringExtra("firingReason");
		boolean canKeepMonitoring = getIntent().getBooleanExtra("canKeepMonitoring", false);
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
		final LayoutInflater inflater = LayoutInflater.from(this);
		final View view = inflater.inflate(R.layout.alarm_alert, null);
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		((TextView) view.findViewById(R.id.firing_reason)).setText(firingReason);
		if(!canKeepMonitoring) {
			view.findViewById(R.id.keep_monitoring_wrapper).setVisibility(View.GONE);
			view.findViewById(R.id.turn_off_wrapper).setLayoutParams(
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT, 2f));
		}
		setContentView(view);
	}

	@Override
	public void onBackPressed() {
		// Don't allow back to dismiss.
	}

	public void onTurnOffClicked(View v) {
		NotificationService.stopNotify(this, alarmID, false);
		finish();
	}

	public void onKeepMonitoringClicked(View v) {
		NotificationService.stopNotify(this, alarmID, true);
		finish();
	}
}