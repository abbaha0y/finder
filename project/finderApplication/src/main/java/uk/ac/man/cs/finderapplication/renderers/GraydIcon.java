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
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

import javax.swing.Icon;

public class GraydIcon implements Icon {
  Icon icon;

  Image image;

  public GraydIcon(Icon icon) {
    this.icon = icon;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    if (image == null) {
      Image orgImage = c.createImage(getIconWidth(), getIconHeight());
      Graphics imageG = orgImage.getGraphics();
      Color background = c.getBackground();
      imageG.setColor(background);
      imageG.fillRect(0, 0, getIconWidth(), getIconHeight());

      icon.paintIcon(c, imageG, x, y);

      ImageFilter colorfilter = new GrayFilter();
      image = c.createImage(new FilteredImageSource(orgImage.getSource(),
          colorfilter));

    }
    g.drawImage(image, x, y, null);
  }

  public int getIconWidth() {
    return icon.getIconWidth();
  }

  public int getIconHeight() {
    return icon.getIconHeight();
  }

  

}
