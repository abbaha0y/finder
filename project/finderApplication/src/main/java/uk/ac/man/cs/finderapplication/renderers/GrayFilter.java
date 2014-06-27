/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.renderers;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
import java.awt.image.RGBImageFilter;

public class GrayFilter extends RGBImageFilter {

    public GrayFilter() {
      // If I set ture, black is gone?!
        //canFilterIndexColorModel = true;
    }

    public int filterRGB(int x, int y, int rgb) {
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0x00ff00) >> 8;
        int b = (rgb & 0x0000ff);
        int iy = (int) (r + g + b) / 3;
        iy = Math.min(255, iy);
        return ((rgb & 0xff000000) | (iy << 16) | (iy << 8) | iy);
    }
}
