package uk.co.cub3d.dscotd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class LoginPageView extends AppCompatActivity
{
	public WebView webView;
	public String codeOfTheDay = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page_view);

		codeOfTheDay = getIntent().getStringExtra(Utils.CODE_OF_THE_DAY_BUNDLE);

		webView = (WebView) findViewById(R.id.loginPageViewer);
		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebViewClient(new CodeInjectionWebViewClient(this, codeOfTheDay));

		if(Utils.DEBUG)
		{
			webView.loadData(Utils.readFileFully(this, R.raw.loginhtml), "text/html", null);
		}
		else
		{
			webView.loadUrl(getResources().getString(R.string.COTD_ENTRY_PAGE_URL));
		}
	}
}
