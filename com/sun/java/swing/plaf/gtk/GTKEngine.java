/*
 * @(#)GTKEngine.java	1.53 04/01/13
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.java.swing.plaf.gtk;

import java.awt.*;
import javax.swing.*;

/**
 * GTKEngine mirrors that of a a GtkStyleClass.
 * <p>
 * The states that are passed in correspond to gtk's set of states:
 * <table>
 * <tr><td>Synth<td>GTK
 * <tr><td>GTKConstants.ENABLED<td>NORMAL
 * <tr><td>GTKConstants.PRESSED<td>ACTIVE
 * <tr><td>GTKConstants.SELECTED<td>SELECTED
 * <tr><td>GTKConstants.MOUSE_OVER<td>PRELIGHT
 * <tr><td>GTKConstants.DISABLED<td>INSENSITIVE
 * </table>
 * @version 1.53, 01/13/04
 * @author Josh Outwater
 * @author Scott Violet
 */
class GTKEngine {
    static final GTKEngine INSTANCE = new GTKEngine();

    static final int[] DEFAULT_FOCUS_PATTERN = new int[] {1, 1};

    static int adjustAxisForComponentOrientation(JComponent c,
                                                 int axis) {
        if (c.getComponentOrientation().isLeftToRight()) {
            return axis;
        }
        switch (axis) {
        case SwingConstants.EAST:
            return SwingConstants.WEST;
        case SwingConstants.WEST:
            return SwingConstants.EAST;
        }
        return axis;
    }


    public void paintArrow(SynthContext context, Graphics g, int state,
                           int shadowType, int direction, String info,
                           int x, int y, int w, int h) {
        // Draw the arrow
        int sizeW = w / 2;
        if (w % 2 == 1) {
            sizeW++;
        }
        int sizeH = h / 2;
        if (h % 2 == 1) {
            sizeH++;
        }
        int size = Math.max(2, Math.min(sizeW, sizeH));

        switch (direction) {
        case GTKConstants.ARROW_UP:
            x += w / 2 - 1;
            y += (h - size) / 2;
            break;
        case GTKConstants.ARROW_DOWN:
            x += w / 2 - 1;
            y += (h - size) / 2 + 1;
            break;
        case GTKConstants.ARROW_LEFT:
            x += (w - size) / 2;
            y += h / 2 - 1;
            break;
        case GTKConstants.ARROW_RIGHT:
            x += (w - size) / 2 + 1;
            y += h / 2 - 1;
            break;
        }

        GTKStyle style = (GTKStyle)context.getStyle();
        int mid, i, j;

        j = 0;
        mid = (size / 2) - 1;

        g.translate(x, y);

        if (state == SynthConstants.DISABLED) {
            g.setColor(style.getGTKColor(context.getComponent(),
                       context.getRegion(), state, GTKColorType.WHITE));
        }
        else {
            g.setColor(style.getGTKColor(context.getComponent(),
                       context.getRegion(), state, GTKColorType.FOREGROUND));
        }

        switch(direction) {
        case GTKConstants.ARROW_UP:
            for(i = 0; i < size; i++) {
                g.drawLine(mid-i, i, mid+i, i);
            }
            break;
        case GTKConstants.ARROW_DOWN:
            j = 0;
            for (i = size-1; i >= 0; i--) {
                g.drawLine(mid-i, j, mid+i, j);
                j++;
            }
            break;
        case GTKConstants.ARROW_LEFT:
            for (i = 0; i < size; i++) {
                g.drawLine(i, mid-i, i, mid+i);
            }
            break;
        case GTKConstants.ARROW_RIGHT:
            j = 0;
            for (i = size-1; i >= 0; i--)   {
                g.drawLine(j, mid-i, j, mid+i);
                j++;
            }
            break;
        }
        g.translate(-x, -y);
    }

    public void paintBox(SynthContext context, Graphics g, int state,
                         int shadowType, String info, int x, int y,
                         int w, int h) {
        GTKStyle style = (GTKStyle)context.getStyle();
        Region region = context.getRegion();
        // This should really happen after the shadow is drawn.
         if (info == "menuitem" && state == SynthConstants.MOUSE_OVER &&
                                   GTKLookAndFeel.is2_2()) {
             paintBackground(context, g, state,
                    style.getGTKColor(context.getComponent(), region,
                  SynthConstants.SELECTED, GTKColorType.BACKGROUND), x, y, w,h);
         }
         else {
             paintBackground(context, g, state,
                style.getGTKColor(context.getComponent(), region, state,
                GTKColorType.BACKGROUND), x, y, w, h);
         }
         paintShadow(context, g, state, shadowType, info, x, y, w, h);
    }

    public void paintBoxGap(SynthContext context, Graphics g, int state,
                            int shadowType, String info, int x, int y,
                            int w, int h, int boxGapType, int tabBegin,
                            int size) {
        _paintBoxGap(context, g, state, shadowType, x, y, w, h, boxGapType,
                     tabBegin, size, GTKColorType.BACKGROUND,
                     GTKColorType.LIGHT, GTKColorType.BLACK,
                     GTKColorType.DARK, false);
    }

    void _paintBoxGap(SynthContext context, Graphics g, int state,
                      int shadowType, int x, int y, int w, int h,
                      int boxGapType, int tabBegin, int size,
                      ColorType backgroundType, ColorType upperLeftType,
                      ColorType bottomRightType, ColorType innerRightType,
                      boolean paintWhite) {
        GTKStyle style = (GTKStyle)context.getStyle();
        JComponent c = context.getComponent();
        Region region = context.getRegion();

        paintBackground(context, g, state, style.getGTKColor(c, region, state,
                        backgroundType), x, y, w, h);
        int tabEnd = tabBegin + size;

        Color upperLeft = style.getGTKColor(c, region, state, upperLeftType);
        Color bottomRight = style.getGTKColor(c, region, state,
                                              bottomRightType);
        Color innerRight = style.getGTKColor(c, region, state, innerRightType);
        Color white = style.getGTKColor(c, region, state, GTKColorType.WHITE);

        g.translate(x, y);
        if (boxGapType == GTKConstants.TOP) {
            // Paint bottom.
            g.setColor(bottomRight);
            g.drawLine(0, h - 1, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(1, h - 2, w - 2, h - 2);

            // Paint left side.
            g.setColor(upperLeft);
            g.drawLine(0, 0, 0, h - 2);
            if (paintWhite) {
                g.setColor(white);
                g.drawLine(1, 0, 1, h - 3);
            }

            // Paint right side.
            g.setColor(bottomRight);
            g.drawLine(w - 1, 0, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(w - 2, 0, w - 2, h - 2);

            // Paint top.
            if (tabBegin > 0) {
                g.setColor(upperLeft);
                g.drawLine(0, 0, tabBegin, 0);
            }
            g.setColor(upperLeft);
            g.drawLine(tabEnd, 0, w - 2, 0);
            if (paintWhite) {
                g.setColor(white);
                if (tabBegin > 0) {
                    g.drawLine(0, 1, tabBegin, 1);
                }
                g.drawLine(tabEnd, 1, w - 2, 1);
            }
        }
        else if (boxGapType == GTKConstants.BOTTOM) {
            // Paint top.
            g.setColor(upperLeft);
            g.drawLine(0, 0, w - 1, 0);
            if (paintWhite) {
                g.setColor(white);
                g.drawLine(0, 1, w - 1, 1);
            }

            // Paint left side.
            g.setColor(upperLeft);
            g.drawLine(0, 0, 0, h - 1);
            if (paintWhite) {
                g.setColor(white);
                g.drawLine(1, 1, 1, h - 2);
            }

            // Paint right side.
            g.setColor(bottomRight);
            g.drawLine(w - 1, 0, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(w - 2, 1, w - 2, h - 2);

            // Paint bottom.
            if (tabBegin > 0) {
                g.setColor(bottomRight);
                g.drawLine(0, h - 1, tabBegin, h - 1);
                g.setColor(innerRight);
                g.drawLine(1, h - 2, tabBegin, h - 2);
            }
            g.setColor(bottomRight);
            g.drawLine(tabEnd, h - 1, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(tabEnd, h - 2, w - 2, h - 2);
        }
        else if (boxGapType == GTKConstants.RIGHT) {
            // Paint top.
            g.setColor(upperLeft);
            g.drawLine(0, 0, w - 1, 0);
            if (paintWhite) {
                g.setColor(white);
                g.drawLine(0, 1, w - 1, 1);
            }

            // Paint bottom.
            g.setColor(bottomRight);
            g.drawLine(0, h - 1, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(1, h - 2, w - 2, h - 2);

            // Paint left side.
            g.setColor(upperLeft);
            g.drawLine(0, 0, 0, h - 2);
            if (paintWhite) {
                g.setColor(white);
                g.drawLine(1, 1, 1, h - 3);
            }

            if (tabBegin > 0) {
                g.setColor(bottomRight);
                g.drawLine(w - 1, 0, w - 1, tabBegin);
                g.setColor(innerRight);
                g.drawLine(w - 2, 1, w - 2, tabBegin);
            }
            g.setColor(bottomRight);
            g.drawLine(w - 1, tabEnd, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(w - 2, tabEnd, w - 2, h - 2);
        }
        else if (boxGapType == GTKConstants.LEFT) {
            // Paint top.
            g.setColor(upperLeft);
            g.drawLine(0, 0, w - 2, 0);
            if (paintWhite) {
                g.setColor(white);
                g.drawLine(0, 1, w - 2, 1);
            }

            // Paint bottom.
            g.setColor(bottomRight);
            g.drawLine(0, h - 1, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(1, h - 2, w - 2, h - 2);

            // Paint right side.
            g.setColor(bottomRight);
            g.drawLine(w - 1, 1, w - 1, h - 1);
            g.setColor(innerRight);
            g.drawLine(w - 2, 0, w - 2, h - 2);

            // Paint left side.
            if (tabBegin > 0) {
                g.setColor(upperLeft);
                g.drawLine(0, 0, 0, tabBegin);
            }
            g.setColor(upperLeft);
            g.drawLine(0, tabEnd, 0, h - 2);
            if (paintWhite) {
                g.setColor(white);
                if (tabBegin > 0) {
                    g.drawLine(1, 0, 1, tabBegin);
                }
                g.drawLine(1, tabEnd, 1, h - 3);
            }
        }
        g.translate(-x, -y);
    }

    public void paintCheck(SynthContext context, Graphics g, int state,
                           int shadowType, String info, int x, int y,
                           int w, int h) {
        int componentState = context.getComponentState();
        JComponent component = context.getComponent();
        Region r = context.getRegion();
        GTKStyle style = (GTKStyle)context.getStyle();

        if ("checkbutton" == info) {
            if ((componentState & SynthConstants.PRESSED) == 0) {
                paintBox(context, g, SynthConstants.ENABLED,
                        GTKConstants.SHADOW_IN, info, x, y, w, h);
                g.setColor(style.getGTKColor(component, r, state,
                        GTKColorType.WHITE));
            } else {
                paintBox(context, g, SynthConstants.PRESSED,
                        GTKConstants.SHADOW_IN, info, x, y, w, h);
                g.setColor(style.getGTKColor(component, r, state,
                        GTKColorType.BACKGROUND));
            }
        }

        g.translate(x, y);

        int centerY = h / 2 - 1;

        if ("checkbutton" == info) {
            g.fillRect(2, 2, w - 4, h - 4);
        }

        // Paint check mark in center if selected.
        if ((componentState & SynthConstants.SELECTED) != 0) {
            g.setColor(
                style.getGTKColor(component, r, state, GTKColorType.FOREGROUND));
            g.drawLine(3, centerY + 2, 4, centerY + 2);
            g.drawLine(5, centerY + 3, 6, centerY + 3);
            g.drawLine(6, centerY + 2, 6, centerY + 4);
            g.drawLine(7, centerY + 1, 7, centerY);
            g.drawLine(7, centerY, 8, centerY);
            g.drawLine(8, centerY, 8, centerY - 1);
            g.drawLine(8, centerY - 1, 9, centerY - 1);
            g.drawLine(9, centerY - 1, 9, centerY - 2);

            if (!GTKLookAndFeel.is2_2()) {
                if ((componentState & SynthConstants.PRESSED) != 0) {
                    g.setColor(style.getGTKColor(component, r,
                    SynthConstants.PRESSED, GTKColorType.DARK));
                } else {
                    g.setColor(style.getGTKColor(component, r,
                    SynthConstants.ENABLED, GTKColorType.DARK));
                }
            }
            g.drawLine(3, centerY + 1, 4, centerY + 1);
            g.drawLine(4, centerY + 3, 4, centerY + 3);
            g.drawLine(5, centerY + 2, 5, centerY + 2);
            g.drawLine(6, centerY + 1, 6, centerY + 1);
            g.drawLine(7, centerY + 2, 7, centerY + 2);
            g.drawLine(10, centerY - 2, 10, centerY - 2);
        }
        g.translate(-x, -y);
    }

    public void paintExtension(SynthContext context, Graphics g, int state,
                               int shadowType, String info, int x, int y,
                               int w, int h, int placement) {
        _paintExtension(context, g, state, shadowType, x, y, w, h, placement,
                        GTKColorType.LIGHT, GTKColorType.BACKGROUND,
                        GTKColorType.BLACK, GTKColorType.DARK, false);
    }

    void _paintExtension(SynthContext context, Graphics g, int state,
                    int shadowType, int x, int y,
                    int w, int h, int placement, ColorType upperLeftType,
                    ColorType backgroundType, ColorType bottomRightType,
                    ColorType innerRightType, boolean paintWhite) {
        GTKStyle style = (GTKStyle)context.getStyle();
        TabContext tabContext = (TabContext)context;
        JComponent c = context.getComponent();
        Region region = context.getRegion();
        int xThickness = style.getXThickness();
        int yThickness = style.getYThickness();

        if (xThickness < 0 && yThickness < 0) {
            // nothing to paint.
            return;
        }

        Color upperLeft =
            style.getGTKColor(c, region, state, upperLeftType);
        Color background =
            style.getGTKColor(c, region, state, backgroundType);
        Color bottomRight =
            style.getGTKColor(c, region, state, bottomRightType);
        Color innerRight =
            style.getGTKColor(c, region, state, innerRightType);
        Color white = style.getGTKColor(c, region, state, GTKColorType.WHITE);

        int tabIndex = tabContext.getTabIndex();
        int selectedIndex = ((JTabbedPane)c).getSelectedIndex();

        g.translate(x, y);

        // NOTE: placement refers to the size of the extension, so that
        // BOTTOM corresponds to a TabbedPane with the tabs placed at the top.
        if (placement == GTKConstants.BOTTOM) {
            // Fill the background.
            paintBackground(context, g, state, background, 0, 1, w, h);

            if (tabIndex == selectedIndex || tabIndex == 0) {
                g.setColor(upperLeft);
                g.drawLine(0, 1, 0, h - 1);
                g.drawLine(1, 0, w - 2, 0);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(1, 1, 1, h - 1);
                    g.drawLine(1, 1, w - 3, 1);
                }
            } else {
                g.setColor(upperLeft);
                g.drawLine(0, 0, w - 2, 0);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(0, 1, w - 3, 1);
                }
            }
            if ((tabIndex + 1) != selectedIndex) {
                g.setColor(innerRight);
                g.drawLine(w - 2, 2, w - 2, h - 1);
                g.setColor(bottomRight);
                g.drawLine(w - 1, 1, w - 1, h - 1);
            }

        } else if (placement == GTKConstants.TOP) {
            // Fill the background.
            paintBackground(context, g, state, background, 0, 0, w, h - 1);

            if (tabIndex == selectedIndex || tabIndex == 0) {
                g.setColor(upperLeft);
                g.drawLine(0, 0, 0, h - 2);
                g.setColor(bottomRight);
                g.drawLine(1, h - 1, w - 2, h - 1);
                g.setColor(innerRight);
                g.drawLine(2, h - 2, w - 2, h - 2);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(1, 0, 1, h - 2);
                }
            } else {
                g.setColor(bottomRight);
                g.drawLine(0, h - 1, w - 2, h - 1);
                g.setColor(innerRight);
                g.drawLine(0, h - 2, w - 2, h - 2);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(0, 0, 0, h - 2);
                }
            }
            if ((tabIndex + 1) != selectedIndex) {
                g.setColor(bottomRight);
                g.drawLine(w - 1, 0, w - 1, h - 2);
                g.setColor(innerRight);
                g.drawLine(w - 2, 0, w - 2, h - 2);
            }
        } else if (placement == GTKConstants.RIGHT) {
            // Fill the background.
            paintBackground(context, g, state, background, 1, 0, w, h);

            if (tabIndex == selectedIndex || tabIndex == 0) {
                g.setColor(upperLeft);
                g.drawLine(1, 0, w - 1, 0);
                g.drawLine(0, 1, 0, h - 2);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(1, 1, w, 1);
                    g.drawLine(1, 1, 1, h - 3);
                }
            } else {
                g.setColor(upperLeft);
                g.drawLine(0, 0, 0, h - 2);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(1, 1, 1, h - 3);
                }
            }
            if ((tabIndex + 1) != selectedIndex) {
                g.setColor(bottomRight);
                g.drawLine(1, h - 1, w - 1, h - 1);
                g.setColor(innerRight);
                g.drawLine(2, h - 2, w - 1, h - 2);
            }
        } else if (placement == GTKConstants.LEFT) {
            // Fill the background.
            paintBackground(context, g, state, background, 0, 0, w - 1, h);

            if (tabIndex == selectedIndex || tabIndex == 0) {
                g.setColor(upperLeft);
                g.drawLine(0, 0, w - 2, 0);
                g.setColor(bottomRight);
                g.drawLine(w - 1, 1, w - 1, h - 2);
                g.setColor(innerRight);
                g.drawLine(w - 2, 2, w - 2, h - 2);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(0, 1, w - 2, 1);
                    g.drawLine(w - 2, 1, w - 2, h - 3);
                }
            } else {
                g.setColor(bottomRight);
                g.drawLine(w - 1, 0, w - 1, h - 2);
                g.setColor(innerRight);
                g.drawLine(w - 2, 0, w - 2, h - 2);
                if (paintWhite) {
                    g.setColor(white);
                    g.drawLine(w - 2, 1, w - 2, h - 3);
                }
            }
            if ((tabIndex + 1) != selectedIndex) {
                g.setColor(bottomRight);
                g.drawLine(0, h - 1, w - 2, h - 1);
                g.setColor(innerRight);
                g.drawLine(0, h - 2, w - 2, h - 2);

            }
        }
        g.translate(-x, -y);
    }

    void paintFlatBoxNormal(SynthContext context, Graphics g, int state,
                             String key, int x, int y, int w, int h) {
        g.setColor(((GTKStyle)context.getStyle()).getGTKColor(
                context.getComponent(), context.getRegion(), state,
                GTKColorType.BACKGROUND));
        paintFlatBox(context, g, state, key, x, y, w, h);
    }

    void paintFlatBoxText(SynthContext context, Graphics g, int state,
                             String key, int x, int y, int w, int h) {
        g.setColor(((GTKStyle)context.getStyle()).getGTKColor(
                context.getComponent(), context.getRegion(), state,
                GTKColorType.TEXT_BACKGROUND));
        paintFlatBox(context, g, state, key, x, y, w, h);
    }

    // NOTE: this assumes you have set the right color before calling this
    public void paintFlatBox(SynthContext context, Graphics g, int state,
                             String key, int x, int y, int w, int h) {
        GTKStyle style = (GTKStyle)context.getStyle();

        if (key == "cell_odd") {
            g.fillRect(x, y, w, h);
        }
        else {
            paintBackground(context, g, state, g.getColor(), x, y, w, h);
        }

        if (key == "tooltip") {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, w - 1, h - 1);
        }
    }

    public void paintFocus(SynthContext context, Graphics g, int state,
                           String key, int x, int y, int w, int h) {
        GTKStyle style = (GTKStyle)context.getStyle();
        int[] pattern = (int[])style.getClassSpecificValue(
                                     context, "focus-line-pattern");
        int size = style.getClassSpecificIntValue(context,
                                                  "focus-line-width", 1);

        if (pattern == null) {
            pattern = DEFAULT_FOCUS_PATTERN;
        }
        if (size <= 0) {
            return;
        }
        g.setColor(getFocusColor(context, state));

        _paintFocus(g, x, y, w, h, pattern, size);
    }

    void _paintFocus(Graphics g, int x, int y, int w, int h, int[] pattern,
                     int size) {
        g.translate(x, y);

        // PENDING: This will need to be optimized
        int initial = size / 2;
        int offset = initial;
        int index = 0;

        int max1 = w - size;
        int max2 = max1 + h - size;
        int max3 = max2 + w - size;
        int max4 = max3 + h;

        while (offset < max4) {
            int next = offset + pattern[index];

            if (index % 2 == 0) {
                if (offset < max1) {
                    // top
                    g.fillRect(offset, 0, Math.min(next, w) - offset, size);
                    offset = Math.min(next, max1);
                }
                if (offset != next && offset < max2) {
                    // right
                    g.fillRect(w - size, offset - max1, size,
                               Math.min(max2, next) - offset);
                    offset = Math.min(max2, next);
                }
                if (offset != next && offset < max3) {
                    // bottom
                    int x2 = w - (offset - max2);
                    int temp = Math.min(next - offset, max3 - offset);
                    g.fillRect(x2 - temp, h - size, temp, size);
                    offset += temp;
                }
                if (offset != next) {
                    int y2 = h - (offset - max3);
                    int temp = Math.min(next - offset, max4 - offset);
                    g.fillRect(0, y2 - temp, size, temp);
                    offset += temp;
                }
            }
            else {
                offset = next;
            }
            index = (index + 1) % pattern.length;
        }

        g.translate(-x, -y);
    }

    Color getFocusColor(SynthContext context, int state) {
        return ((GTKStyle)context.getStyle()).getGTKColor(
               context.getComponent(), context.getRegion(), state,
               ColorType.FOREGROUND);
    }

    public void paintHandle(SynthContext context, Graphics g, int paintState,
                            int shadowType, String info, int x, int y,
                            int w, int h, int orientation) {
        paintBox(context, g, paintState, shadowType, info, x, y, w, h);

        g.translate(x, y);

        Region region = context.getRegion();
        GTKStyle style = (GTKStyle)context.getStyle();
        JComponent component = context.getComponent();
        Color lightColor = style.getGTKColor(component, region, paintState,
                                          GTKColorType.LIGHT);
        Color darkColor = style.getGTKColor(component, region, paintState,
                                          GTKColorType.DARK);
        Color blackColor = style.getGTKColor(component, region, paintState,
                                          GTKColorType.BLACK);
        int startX;
        int startY;
        if (info == "paned") {
            JSplitPane splitPane = (JSplitPane)component;

            int markerLength = 7 * 3 + 6 * 2;  // 7 markers width of 3
                                               // + 6 spacers width of 2.
            if (orientation == GTKConstants.HORIZONTAL) {
                // Components are above/below.
                startX = w / 2 - markerLength / 2;
                startY = splitPane.getDividerSize() / 2 - 1;
            } else {
                // Components are left/right
                startX = splitPane.getDividerSize() / 2 - 1;
                startY = h / 2 - markerLength / 2;
            }

            for (int i = 0; i < 7; i++) {
                // Add length of one marker and spacing.
                if (orientation == GTKConstants.HORIZONTAL) {
                    startX += 5;
                } else {
                    startY += 5;
                }
                g.setColor(lightColor);
                g.fillRect(startX, startY, 2, 2);
                g.setColor(blackColor);
                g.fillRect(startX + 1, startY + 1, 2, 2);
                g.setColor(darkColor);
                g.fillRect(startX + 1, startY + 1, 1, 1);
            }
        } else if (info == "handlebox") {
            JToolBar tb = (JToolBar)component;
            startX = 2;
            startY = 2;
            if (tb.getOrientation() == JToolBar.HORIZONTAL) {
                while (startY < h) {
                    g.setColor(darkColor);
                    g.fillRect(startX + 1, startY + 1, 2, 2);
                    g.fillRect(startX + 4, startY + 2, 2, 2);
                    g.setColor(lightColor);
                    g.fillRect(startX, startY, 2, 2);
                    g.fillRect(startX + 3, startY + 1, 2, 2);
                    startY += 3;
                }
            } else {
                boolean toggle = true;
                while (startX < w) {
                    g.setColor(darkColor);
                    g.fillRect(startX + 1, startY + 1, 2, 2);
                    g.fillRect(startX + 1, startY + 4, 2, 2);
                    g.setColor(lightColor);
                    g.fillRect(startX, startY, 2, 2);
                    g.fillRect(startX, startY + 3, 2, 2);
                    startX += 3;
                    if (toggle) {
                        startY++;
                        toggle = false;
                    } else {
                        startY--;
                        toggle = true;
                    }
                }
            }
            // Fix up the shadow.
            paintShadow(context, g, paintState, shadowType, info, 0, 0, w, h);
        }
        g.translate(-x, -y);
    }

    public void paintOption(SynthContext context, Graphics g, int paintState,
                            int shadowType, String info, int x, int y,
                            int w, int h) {
        g.translate(x, y);

        int componentState = context.getComponentState();
        int centerY = h / 2 - 1;
        JComponent component = context.getComponent();
        Region region = context.getRegion();
        GTKStyle style = (GTKStyle)context.getStyle();

        // If the radio button is being pressed we fill the center with the
        // MID gtk color, otherwise we fill it WHITE.  We need to use the state
        // from the context since the state passed in may have been modified.
        if ((componentState & SynthConstants.PRESSED) == 0) {
            g.setColor(style.getGTKColor(component, region, paintState,
                                         GTKColorType.WHITE));
        } else {
            g.setColor(style.getGTKColor(component, region, paintState,
                                         GTKColorType.MID));
        }

        if (region != Region.RADIO_BUTTON_MENU_ITEM) {
            // Paint center portion.
            g.fillOval(1, 0 , 11, 11);
            g.drawLine(13, centerY - 1, 13, centerY + 2);
            g.drawLine(12, centerY + 3, 12, centerY + 4);
            g.drawLine(11, centerY + 5, 11, centerY + 5);
            g.drawLine(9, centerY + 6, 10, centerY + 6);
            g.drawLine(5, centerY + 7, 8, centerY + 7);

            // Paint dark shadow.
            g.setColor(style.getGTKColor(component, region, paintState,
                                         GTKColorType.DARK));
            g.drawOval(1, 0, 11, 11);

            // Paint black shadow.
            g.setColor(style.getGTKColor(component, region, paintState,
                                         GTKColorType.BLACK));
            g.drawLine(5, centerY - 4, 8, centerY - 4);
            g.drawLine(9, centerY - 4, 10, centerY - 3);
            g.drawLine(3, centerY - 3, 4, centerY - 3);
            g.drawLine(3, centerY - 2, 2, centerY - 1);
            g.drawLine(2, centerY, 2, centerY + 2);
            g.drawLine(2, centerY + 3, 3, centerY + 4);
        }

        // Fill in center if selected.
        if ((componentState & SynthConstants.SELECTED) != 0) {
            if (region == Region.RADIO_BUTTON_MENU_ITEM) {
                g.setColor(style.getGTKColor(component, region, paintState,
                                             GTKColorType.FOREGROUND));
            }
            g.fillRect(5, centerY, 5, 3);
            g.drawLine(6, centerY - 1, 8, centerY - 1);
            g.drawLine(6, centerY + 3, 8, centerY + 3);
        }
        g.translate(-x, -y);
    }

    public void paintShadow(SynthContext context, Graphics g, int state,
                            int shadowType, String info, int x, int y,
                            int w, int h) {
        if (info == "buttondefault") {
            // YES, this appears to be special cased.
            g.setColor(((GTKStyle)context.getStyle()).getGTKColor(
                           context.getComponent(), context.getRegion(),
                           state, GTKColorType.BLACK));
            g.drawRect(x, y, w - 1, h - 1);
            return;
        }
        GTKStyle style = (GTKStyle)context.getStyle();
        JComponent c = context.getComponent();
        Region region = context.getRegion();
        int xThickness = style.getXThickness();
        int yThickness = style.getYThickness();

        if (info == "trough" ||
                (info == "spinbutton" && c instanceof JButton)) {
            // YES, this appears to be special cased.
            xThickness = yThickness = 1;
        }

        if (xThickness < 0 && yThickness < 0) {
            // nothing to paint.
            return;
        }
        Color upperLeft = null, innerLeft = null, bottomRight = null,
              innerRight = null;
        switch (shadowType) {
        case GTKConstants.SHADOW_IN:
            if (info == "spinbutton" && c instanceof JButton) {
                upperLeft = style.getGTKColor(c, region, state,
                        GTKColorType.DARK);
                innerRight = upperLeft;
                bottomRight = style.getGTKColor(c, region, state,
                        GTKColorType.BLACK);
            } else {
                upperLeft = style.getGTKColor(c, region, state,
                        GTKColorType.DARK);
                innerLeft = style.getGTKColor(c, region, state,
                        GTKColorType.BLACK);
                bottomRight = style.getGTKColor(c, region, state,
                        GTKColorType.LIGHT);
                innerRight = style.getGTKColor(c, region, state,
                        GTKColorType.BACKGROUND);
            }
            break;
        case GTKConstants.SHADOW_OUT:
            upperLeft = style.getGTKColor(c, region, state,GTKColorType.LIGHT);
            innerLeft = style.getGTKColor(c, region, state,
                                          GTKColorType.BACKGROUND);
            bottomRight = style.getGTKColor(c, region, state,
                                            GTKColorType.BLACK);
            innerRight = style.getGTKColor(c, region, state,GTKColorType.DARK);
            break;
        default:
            assert true : "Unknown shadow type!";
        }
        if (info == "spinbutton" && c instanceof JButton) {
            _paintSpinButtonShadow(g, x, y, w, h, xThickness, yThickness,
                    upperLeft, innerLeft, bottomRight, innerRight);
        } else {
            _paintShadow(g,  x, y, w, h, xThickness, yThickness,
                    upperLeft, innerLeft, bottomRight, innerRight);
        }
    }

    void _paintShadow(Graphics g, int x, int y, int w, int h,
                 int xThickness, int yThickness, Color upperLeft,
                 Color innerLeft, Color bottomRight, Color innerRight) {
        g.translate(x, y);
        // left/top
        g.setColor(upperLeft);
        if (xThickness > 0) {
            g.drawLine(0, 0, 0, h - 1);
        }
        if (yThickness > 0) {
            g.drawLine(1, 0, w - 1, 0);
        }

        // Inner left/top
        if (xThickness > 1 || yThickness > 1) {
            g.setColor(innerLeft);
            if (xThickness > 1) {
                g.drawLine(1, 1, 1, h - 2);
            }
            if (yThickness > 1) {
                g.drawLine(2, 1, w - 2, 1);
            }
        }

        // Bottom/right
        g.setColor(bottomRight);
        if (xThickness > 0) {
            g.drawLine(w - 1, 1, w - 1, h - 1);
        }
        if (yThickness > 0) {
            g.drawLine(1, h - 1, w - 2, h - 1);
        }
        // Inner bottom/right
        if (xThickness > 1 || yThickness > 1) {
            g.setColor(innerRight);
            if (xThickness > 1) {
                g.drawLine(w - 2, 2, w - 2, h - 2);
            }
            if (yThickness > 1) {
                g.drawLine(2, h - 2, w - 3, h - 2);
            }
        }
        g.translate(-x, -y);
    }

    void _paintSpinButtonShadow(Graphics g, int x, int y,
                 int w, int h, int xThickness, int yThickness, Color upperLeft,
                 Color innerLeft, Color bottomRight, Color innerRight) {
        g.translate(x, y);

        // top
        g.setColor(upperLeft);
        g.drawLine(0, 0, w - 1, 0);
        // bottom
        g.setColor(innerRight);
        g.drawLine(0, h - 1, w - 1, h - 1);
        // left/right
        g.setColor(bottomRight);
        g.drawLine(0, 0, 0, h - 1);
        g.drawLine(w - 1, 0, w - 1, h - 1);

        g.translate(-x, -y);
    }

    public void paintExpander(SynthContext context, Graphics g, int state,
                              int expanderStyle, String info, int x,
                              int y, int w, int h) {
        if (expanderStyle == GTKConstants.EXPANDER_COLLAPSED) {
            if (state != SynthConstants.MOUSE_OVER) {
                paintHollowTriangle(context, g, state,
                    x, y, Math.min(w,h),
                    adjustAxisForComponentOrientation(
                    context.getComponent(), SwingConstants.EAST));
            } else {
            }
        }
        else {
            if (state != SynthConstants.MOUSE_OVER) {
                paintHollowTriangle(context, g, state,
                    x, y, Math.min(w,h), SwingConstants.SOUTH);
            } else {
            }
        }
    }

    private void paintHollowTriangle(SynthContext ss, Graphics g, int state,
                                     int x, int y, int size, int direction) {
        GTKStyle style = (GTKStyle)ss.getStyle();
        int mid, height, thick, i, j, up, down;

        mid = (size / 2);
        height = size / 2 + 1;
        thick = Math.max(1, size / 7);

        g.translate(x, y);

        Color foreground = style.getGTKColor(ss.getComponent(), ss.getRegion(),
                state, GTKColorType.FOREGROUND);
        Color background = style.getGTKColor(ss.getComponent(), ss.getRegion(),
                state, GTKColorType.BACKGROUND);

        switch(direction) {
        case SwingConstants.NORTH:
            j = size / 2 + height / 2 - 1;
            // Fill in the background of the expander icon.
            g.setColor(background);
            for(i = height - 1; i > 0; i--) {
                g.drawLine(mid - i + 1, j, mid + i - 1, j);
                j--;
            }

            g.setColor(foreground);
            j = size / 2 + height / 2 - 1;
            down = thick - 1;
            // Draw the base of the triangle.
            for (up = 0; up < thick; up++) {
                g.drawLine(0 - down, j - up, size + down, j - up);
                down--;
            }
            j--;

            // Paint sides of triangle.
            for(i = height - 1; i > 0; i--) {
                for (up = 0; up < thick; up++ ) {
                    g.drawLine(mid - i + 1 - up, j, mid - i + 1 - up, j);
                    g.drawLine(mid + i - 1 + up, j, mid + i - 1 + up, j);
                }
                j--;
            }

            // Paint remainder of tip if necessary.
            if (thick > 1) {
                for (up = thick - 2; up >= 0; up--) {
                    g.drawLine(mid - up, j, mid + up, j);
                    j--;
                }
            }
            break;
        case SwingConstants.SOUTH:
            j = size / 2 - height / 2 - 1;
            // Fill in the background of the expander icon.
            g.setColor(background);
            for (i = height - 1; i > 0; i--) {
                g.drawLine(mid - i + 1, j, mid + i - 1, j);
                j++;
            }

            g.setColor(foreground);
            j = size / 2 - height / 2 - 1;
            down = thick - 1;
            // Draw the base of the triangle.
            for (up = 0; up < thick; up++) {
                g.drawLine(0 - down, j + up, size + down, j + up);
                down--;
            }
            j++;

            // Paint sides of triangle.
            for (i = height - 1; i > 0; i--) {
                for (up = 0; up < thick; up++ ) {
                    g.drawLine(mid - i + 1 - up, j, mid - i + 1 - up, j);
                    g.drawLine(mid + i - 1 + up, j, mid + i - 1 + up, j);
                }
                j++;
            }

            // Paint remainder of tip if necessary.
            if (thick > 1) {
                for (up = thick - 2; up >= 0; up--) {
                    g.drawLine(mid - up, j, mid + up, j);
                    j++;
                }
            }
            break;
        case SwingConstants.WEST:
            i = size / 2 + height / 2 - 1;
            // Fill in the background of the expander icon.
            g.setColor(background);
            for (j = height - 1; j > 0; j--) {
                g.drawLine(i, mid - j + 1, i, mid + j - 1);
                i--;
            }

            g.setColor(foreground);
            i = size / 2 + height / 2 - 1;
            down = thick - 1;
            // Draw the base of the triangle.
            for (up = 0; up < thick; up++) {
                g.drawLine(i - up, 0 - down, i - up, size + down);
                down--;
            }
            i--;

            // Paint sides of triangle.
            for (j = height - 1; j > 0; j--) {
                for (up = 0; up < thick; up++) {
                    g.drawLine(i, mid - j + 1 - up, i, mid - j + 1 - up);
                    g.drawLine(i, mid + j - 1 + up, i, mid + j - 1 + up);
                }
                i--;
            }

            // Paint remainder of tip if necessary.
            if (thick > 1) {
                for (up = thick - 2; up >= 0; up--) {
                    g.drawLine(i, mid - up, i, mid + up);
                    i--;
                }
            }
            break;
        case SwingConstants.EAST:
            i = size / 2 - height / 2 - 1;

            // Fill in the background of the expander icon.
            g.setColor(background);
            for (j = height - 1; j > 0; j--) {
                g.drawLine(i, mid - j + 1, i, mid + j - 1);
                i++;
            }

            g.setColor(foreground);
            i = size / 2 - height / 2 - 1;
            down = thick - 1;
            // Draw the base of the triangle.
            for (up = 0; up < thick; up++) {
                g.drawLine(i + up, 0 - down, i + up, size + down);
                down--;
            }
            i++;

            // Paint sides of triangle.
            for (j = height - 1; j > 0; j--) {
                for (up = 0; up < thick; up++) {
                    g.drawLine(i, mid - j + 1 - up, i, mid - j + 1 - up);
                    g.drawLine(i, mid + j - 1 + up, i, mid + j - 1 + up);
                }
                i++;
            }

            // Paint remainder of tip if necessary.
            if (thick > 1) {
                for (up = thick - 2; up >= 0; up--) {
                    g.drawLine(i, mid - up, i, mid + up);
                    i++;
                }
            }
            break;
        }
        g.translate(-x, -y);
    }

    public void paintSlider(SynthContext context, Graphics g, int state,
                           int shadowType, String info,
                           int x, int y, int w, int h, int orientation) {
        paintBox(context, g, state, shadowType, info, x, y, w, h);
        if (context.getRegion() == Region.SLIDER_THUMB) {
            if (orientation == GTKConstants.HORIZONTAL) {
                paintVline(context, g, state, info, x + w / 2 - 1, y + 2, 2,
                           h - 4);
            } else {
                paintHline(context, g, state, info, x + 2, y + h / 2 - 1,
                           w - 4, 2);
            }
        }
    }

    public void paintHline(SynthContext context, Graphics g, int state,
                           String info, int x, int y, int w, int h) {
        g.translate(x, y);

        GTKStyle style = (GTKStyle)context.getStyle();
        int centerY = h / 2;
        if (h == 2) {
            centerY = 0;
        }
        g.setColor(style.getGTKColor(context.getComponent(),
                                     context.getRegion(), state,
                                     GTKColorType.DARK));
        g.drawLine(0, centerY, w - 2, centerY);
        g.setColor(style.getGTKColor(context.getComponent(),
                                     context.getRegion(), state,
                                     GTKColorType.LIGHT));
        g.drawLine(0, centerY + 1, w - 1, centerY + 1);
        g.drawLine(w - 1, centerY, w - 1, centerY + 1);

        g.translate(-x, -y);
    }
    public void paintVline(SynthContext context, Graphics g, int state,
                           String info, int x, int y, int w, int h) {
        g.translate(x, y);

        GTKStyle style = (GTKStyle)context.getStyle();
        int centerX = w / 2;
        g.setColor(style.getGTKColor(context.getComponent(),
                                     context.getRegion(),
                                     state, GTKColorType.DARK));
        g.drawLine(centerX, 0, centerX, h - 2);
        g.drawLine(centerX, 0, centerX + 1, 0);
        g.setColor(style.getGTKColor(context.getComponent(),
                                     context.getRegion(),
                                     state, GTKColorType.LIGHT));
        g.drawLine(centerX + 1, 1, centerX + 1, h - 1);
        g.drawLine(centerX, h - 1, centerX + 1, h - 1);

        g.translate(-x, -y);
    }


    /**
     * If necessary paints the background.
     */
    void paintBackground(SynthContext context, Graphics g, int state,
                                 Color color, int x, int y, int w, int h) {
        GTKStyle style = (GTKStyle)context.getStyle();

        if (style.fillBackground(context, state)) {
            g.setColor(color);
            g.fillRect(x, y, w, h);
        }
        else {
            Image image = style.getBackgroundImage(context, state);
            int iW;
            int iH;

            if (image != null && (iW = image.getWidth(null)) > 0 &&
                                 (iH = image.getHeight(null)) > 0) {
                // Map the x/y location to originate from the origin of the
                // window
                int x2 = x;
                int y2 = y;
                Component parent = context.getComponent().getParent();

                while (parent != null && !(parent instanceof Window) &&
                       !(parent instanceof java.applet.Applet)) {
                    Component nextParent = parent.getParent();

                    if (parent instanceof JRootPane &&
                               !(nextParent instanceof Window) &&
                               !(nextParent instanceof java.applet.Applet)) {
                        x2 += parent.getX();
                        y2 += parent.getY();
                    }
                    parent = nextParent;
                }
                x2 = x2 % iW;
                y2 = y2 % iH;

                Rectangle clip = g.getClipRect();
                int cx1 = clip.x;
                int cy1 = clip.y;
                int cx2 = cx1 + clip.width;
                int cy2 = cy1 + clip.height;

                int lastIY = y2;
                for (int yCounter = y, maxY = y + h; yCounter < maxY;
                         yCounter += (iH - lastIY), lastIY = 0) {
                    int lastIX = x2;
                    for (int xCounter = x, maxX = x + w; xCounter < maxX;
                             xCounter += (iW - lastIX), lastIX = 0) {
                        int dx2 = Math.min(maxX, xCounter + iW - lastIX);
                        int dy2 = Math.min(maxY, yCounter + iH - lastIY);
                        if (dx2 > cx1 && dy2 > cy1 && cx2 > xCounter &&
                                         cy2 > yCounter) {
                            g.drawImage(image, xCounter, yCounter, dx2, dy2,
                                    lastIX, lastIY, lastIX + dx2 - xCounter,
                                    lastIY + dy2 - yCounter, null);
                        }
                    }
                }
            }
        }
    }
}
