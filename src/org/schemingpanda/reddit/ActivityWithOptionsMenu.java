package org.schemingpanda.reddit;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * An {@link Activity} for creation of the options menu as well as handling menu-selection events.
 * Any activity wishing to allow for the 'main' options menu should extend this class.
 * 
 * @author William Anthony Hunt
 */
public class ActivityWithOptionsMenu extends Activity {

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    
    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.login_menu_item:
        	Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        	startActivity(intent);
        	break;
        case R.id.logout_menu_item:
        	UserState.loggedOut();
        default:
        	return super.onOptionsItemSelected(item);
        }
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu ){
    	
    	if(UserState.isLoggedIn()){
    		menu.findItem(R.id.logout_menu_item).setVisible(true);
    		menu.findItem(R.id.login_menu_item).setVisible(false);
    	}
    	else{
    		menu.findItem(R.id.login_menu_item).setVisible(true);
    		menu.findItem(R.id.logout_menu_item).setVisible(false);
    	}
    	
    	return true;
    }
	
}
