package uk.co.cub3d.dscotd;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Callum on 08/03/2017.
 */

public class CodeInjectionWebViewClient extends WebViewClient
{
	public String codeOfTheDay = "";
	public LoginPageView loginPageView;
	public int redirectCount = 0;
	public String[] possiblePrefixes = {"Injecting", "Inserting", "Pasting", "Adding", "Writing"};

	public CodeInjectionWebViewClient(LoginPageView loginPageView, String codeOfTheDay)
	{
		this.codeOfTheDay = codeOfTheDay;
		this.loginPageView = loginPageView;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		view.loadUrl(url);
		return true;
	}

	@Override
	public void onPageFinished(WebView view, String url)
	{
		redirectCount++;

		if(redirectCount >= 2)
		{
			MainActivity.mainActivity.finishAndRemoveTask();
		}

		//Load the js to inject
		String content = Utils.readFileFully(R.raw.js_inject);
		//Insert the CoTD
		content = content.replace("PASSWORD", codeOfTheDay);
		//Execute it
		loginPageView.webView.evaluateJavascript(content, null);
		//Show a notification (easier than changing the page)
		Toast.makeText(loginPageView, possiblePrefixes[new Random().nextInt(possiblePrefixes.length-1)] + " CoTD", Toast.LENGTH_SHORT).show();
	}
}
