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
package com.crowni.gdx.taileffect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Queue;

/**
 * @author Crowni
 */
public class ActorTailEffect extends Actor {
    private final Queue<Float> tailY = new Queue<Float>();
    private final Queue<Float> tailX = new Queue<Float>();

    // for actor body
    private Sprite body;
    // for tail of actor with different sprite
    private Sprite tail;

    // length of tail
    private int length = 0;
    // alpha for tail effect drawing
    private float alphaModulation = 0;
    // scale for tail effect drawing
    private float scaleModulation = 0;

    // The effect of shape tail #TialEffect.Normal as default value.
    private TailEffect effect = TailEffect.Normal;

    public enum TailEffect {
        /**
         * no scale on all tail
         **/
        Normal,

        /**
         * scale DOWN on the end of tail
         **/
        ScaleDownEnd,

        /**
         * scale DOWN on the head of tail
         **/
        ScaleDownHead,

        /**
         * scale UP on the end of tail
         **/
        ScaleUpEnd,

        /**
         * scale UP on the head of tail
         **/
        ScaleUpHead,
    }

    /**
     * @param body which actor and its tail have same {@link Texture}
     */
    public ActorTailEffect(Texture body) {
        this(body, body);
    }

    /**
     * @param body for actor body
     * @param tail for actor tail
     */
    public ActorTailEffect(Texture body, Texture tail) {
        // setLinearFilter(body, tail);

        // body sprite
        this.body = new Sprite(body);
        this.body.setOriginCenter();

        // tail sprite
        this.tail = new Sprite(tail);
        this.tail.setOriginCenter();

        this.setSize(body.getWidth(), body.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // draw tail under the body
        drawTail(batch);
        // draw body above the tail
        drawBody(batch);

        // update coordinates of tail
        updateTail();
    }

    private void updateTail() {
        // addLast to Queue
        tailX.addLast(this.getX());
        tailY.addLast(this.getY());

        // we want to keep last 20 positions
        if (tailX.size > length) {
            tailX.removeFirst();
            tailY.removeFirst();
        }
    }

    private void drawTail(Batch batch) {
        for (int i = 0, j = tailX.size; i < tailX.size; i++, j--) {
            alphaModulation = i / (float) tailX.size;
            switch (effect) {
                case Normal:
                    scaleModulation = 0;
                    break;
                case ScaleDownEnd:
                    scaleModulation = j / (float) tailX.size;
                    break;
                case ScaleUpEnd:
                    scaleModulation = -j / (float) tailX.size;
                    break;
                case ScaleDownHead:
                    scaleModulation = alphaModulation;
                    break;
                case ScaleUpHead:
                    scaleModulation = -alphaModulation;
                    break;
                default:
                    break;
            }
            tail.setBounds(tailX.get(i), tailY.get(i), this.getWidth(), this.getHeight());
            tail.setScale(this.getScaleX() - scaleModulation, this.getScaleY() - scaleModulation);
            tail.setRotation(this.getRotation());
            tail.setOrigin(this.getOriginX(), this.getOriginY());
            tail.draw(batch, alphaModulation);
        }
    }

    private void drawBody(Batch batch) {
        body.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        body.setScale(this.getScaleX(), this.getScaleY());
        body.setRotation(this.getRotation());
        body.setOrigin(this.getOriginX(), this.getOriginY());
        body.draw(batch);
    }


    /**
     * @param length of tail
     */
    public void setTailLength(int length) {
        this.length = length;
    }

    /**
     * @param effect of tail {@link TailEffect} <br>
     *               {@link TailEffect#Normal} as default value.</br>
     */
    public void setTailEffect(TailEffect effect) {
        this.effect = effect;
    }

    /**
     * @param color of body
     */
    public void setBodyColor(Color color) {
        body.setColor(color);
    }

    /**
     * @param color of tail
     */
    public void setTailColor(Color color) {
        tail.setColor(color);
    }
}
