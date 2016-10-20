package com.android.shortcutapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateShortut;
    private Button btnRemoveShortut;
    private Button btnDisableShortut;
    private EditText etLinkText;
    private EditText etShortDesc;
    private EditText etLongDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btnCreateShortut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShortCut();
            }
        });

        btnRemoveShortut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeShortCut();
            }
        });

        btnDisableShortut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableShortcut();
            }
        });
    }

    private void createShortCut() {
        String shortCutLink = etLinkText.getText().toString();
        String shortDesc = etShortDesc.getText().toString();
        String longDesc = etLongDesc.getText().toString();

        if (TextUtils.isEmpty(shortCutLink) || TextUtils.isEmpty(shortDesc) || TextUtils.isEmpty(longDesc)) {
            Toast.makeText(this, "Fields can't empty", Toast.LENGTH_SHORT).show();
            return;
        }
        ShortcutManager shortcutManager = getShortcutManager();
        // Use Random Number for create Multiple Shortcut.
        Random r = new Random();
        ShortcutInfo shortCutInfo = new ShortcutInfo.Builder(this, "Link" + r.nextInt(99))
                .setShortLabel(shortDesc)
                .setLongLabel(longDesc)
                .setIcon(Icon.createWithResource(this, R.drawable.ic_web_shortcut))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(shortCutLink)))
                .build();
        try { // Check if Shortcut not more then 4
            shortcutManager.addDynamicShortcuts(Arrays.asList(shortCutInfo));
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeShortCut() {
        AlertDialog.Builder builderRemoveShortCut = new AlertDialog.Builder(this);
        builderRemoveShortCut.setTitle("Remove Shortcut");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        final ShortcutManager sMgr = getShortcutManager();
        final List<ShortcutInfo> listShortCut = sMgr.getDynamicShortcuts();
        if (listShortCut.size() == 0) {
            Toast.makeText(this, "Dynamic Shortcut Not Found", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ShortcutInfo sInfo : listShortCut) {
            arrayAdapter.add(sInfo.getShortLabel().toString());
        }

        builderRemoveShortCut.setNegativeButton("Cancel", null);

        builderRemoveShortCut.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sMgr.removeDynamicShortcuts(Arrays.asList(listShortCut.get(which).getId()));
                    }
                });
        builderRemoveShortCut.show();
    }

    private void disableShortcut() {
        AlertDialog.Builder builderDisableShortcut = new AlertDialog.Builder(this);
        builderDisableShortcut.setTitle("Disable Shortcut");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        final ShortcutManager sMgr = getShortcutManager();
        final List<ShortcutInfo> listShortCut = sMgr.getDynamicShortcuts();
        if (listShortCut.size() == 0) {
            Toast.makeText(this, "Dynamic Shortcut Not Found", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ShortcutInfo sInfo : listShortCut) {
            arrayAdapter.add(sInfo.getShortLabel().toString());
        }

        builderDisableShortcut.setNegativeButton("Cancel", null);

        builderDisableShortcut.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sMgr.disableShortcuts(Arrays.asList(listShortCut.get(which).getId()));
                    }
                });
        builderDisableShortcut.show();
    }

    private void initViews() {
        btnCreateShortut = (Button) findViewById(R.id.btn_create_shortcut);
        btnRemoveShortut = (Button) findViewById(R.id.btn_remove_shortcut);
        btnDisableShortut = (Button) findViewById(R.id.btn_disable_shortcut);
        etLinkText = (EditText) findViewById(R.id.et_link);
        etShortDesc = (EditText) findViewById(R.id.et_short_desc);
        etLongDesc = (EditText) findViewById(R.id.et_long_desc);
    }

    private ShortcutManager getShortcutManager() {
        // Get ShortcutManager Instance
        return getSystemService(ShortcutManager.class);
    }
}
