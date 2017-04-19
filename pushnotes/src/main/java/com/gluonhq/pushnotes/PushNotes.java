/**
 * Copyright (c) 2016, 2017 Gluon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of Gluon, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.pushnotes;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.RuntimeArgsService;
import com.gluonhq.pushnotes.views.AppViewManager;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.cloudlink.client.push.PushClient;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PushNotes extends MobileApplication {

    public static final String POPUP_FILTER_NOTES = "Filter Notes";
    private static final String FIREBASE_SENDER_ID = "FIREBASE_SENDER_ID";
    
    @Override
    public void init() {
        AppViewManager.registerViewsAndDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.LIGHT_GREEN.assignTo(scene);

        scene.getStylesheets().add(PushNotes.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(PushNotes.class.getResourceAsStream("/icon.png")));
        
        Services.get(RuntimeArgsService.class).ifPresent(ras -> {
            ras.addListener(RuntimeArgsService.LAUNCH_PUSH_NOTIFICATION_KEY, f -> {
                System.out.println("Received a push notification with contents: " + f);
            });
        });
        
        PushClient pushClient = new PushClient();
        pushClient.enable(FIREBASE_SENDER_ID);
        
    }
}
