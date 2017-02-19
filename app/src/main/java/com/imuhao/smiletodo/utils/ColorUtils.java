package com.imuhao.smiletodo.utils;

import java.util.Random;

/**
 * @author Smile
 * @time 2017/2/19  下午2:34
 * @desc ${TODD}
 */
public class ColorUtils {

  public static String getRandColorCode() {
    String r, g, b;
    Random random = new Random();
    r = Integer.toHexString(random.nextInt(256)).toUpperCase();
    g = Integer.toHexString(random.nextInt(256)).toUpperCase();
    b = Integer.toHexString(random.nextInt(256)).toUpperCase();

    r = r.length() == 1 ? "0" + r : r;
    //        g = g.length() == 1 ? "0" + g : g;
    g = Integer.toHexString(180);
    b = b.length() == 1 ? "0" + b : b;

    return "#" + r + g + b;
  }
}
