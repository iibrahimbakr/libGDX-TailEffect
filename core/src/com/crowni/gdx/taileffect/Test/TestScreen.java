/*
 * *****************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.crowni.gdx.taileffect.Test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.crowni.gdx.taileffect.ActorTailEffect;
import com.crowni.gdx.taileffect.utils.BaseScreen;

/**
 * @author Crowni
 **/
public class TestScreen extends BaseScreen {
    private static final String TAG = TestScreen.class.getSimpleName();

    // to make ball rotate as a circle rotation.
    private Circle circle;
    // as a time [sin f(t)]
    private float angle;

    private ActorTailEffect ball;

    @Override
    public void show() {
        super.show();

        final float width = stage.getWidth();
        final float height = stage.getHeight();

        ball = new ActorTailEffect(new Texture("ball.png"));
        ball.setSize(50, 50);
        ball.setOrigin(Align.center);
        ball.setBodyColor(Color.valueOf("c62369"));
        ball.setTailColor(Color.valueOf("dd80a8"));
        ball.setTailEffect(ActorTailEffect.TailEffect.ScaleDownEnd);
        ball.setTailLength(80);
        stage.addActor(ball);

        // make rotation of ball in center of stage with diameter half of stage width.
        circle = new Circle(width / 2, height / 2, width / 4);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        angle += delta * 2F;

        ball.setPosition(
                circle.x + circle.radius * MathUtils.sin(angle),
                circle.y + circle.radius * MathUtils.cos(angle),
                Align.center);
    }
}
