package com.stuart.tourny.model.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import base.TestBase;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class SqlUtilsTest extends TestBase {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testStb_Upper_Y() throws Exception {
    String input = "Y";
    boolean result = SqlUtils.stb(input);
    assertTrue(result);
  }

  @Test
  public void testStb_Lower_y() throws Exception {
    String input = "y";
    boolean result = SqlUtils.stb(input);
    assertTrue(result);
  }

  @Test
  public void testStb_Word_1() throws Exception {
    String input = "Yeah";
    boolean result = SqlUtils.stb(input);
    assertFalse(result);
  }

  @Test
  public void testStb_Word_2() throws Exception {
    String input = "yeah";
    boolean result = SqlUtils.stb(input);
    assertFalse(result);
  }

  @Test
  public void testStb_Null() throws Exception {
    String input = null;
    boolean result = SqlUtils.stb(input);
    assertFalse(result);
  }

  @Test
  public void testBts_True() throws Exception {
    boolean input = true;
    String result = SqlUtils.bts(input);
    assertEquals("Y", result);
  }

  @Test
  public void testBts_False() throws Exception {
    boolean input = false;
    String result = SqlUtils.bts(input);
    assertEquals("N", result);
  }

  @Test
  public void testMaybeNull_positive() throws Exception {
    long input = 3;
    Long result = SqlUtils.maybeNull(input);
    assertEquals(input,result.longValue());
  }

  @Test
  public void testMaybeNull_negative() throws Exception {
    long input = -1;
    Long result = SqlUtils.maybeNull(input);
    assertEquals(Long.valueOf(input), result);
  }

  @Test
  public void testGetBoolFromResults_positive() throws Exception {
    List<Object> input = new ArrayList<>();
    input.add("Y");
    boolean result = SqlUtils.getBoolFromResults(input,0);
    assertTrue(result);
  }

  @Test
  public void testGetBoolFromResults_negative() throws Exception {
    List<Object> input = new ArrayList<>();
    input.add("N");
    boolean result = SqlUtils.getBoolFromResults(input,0);
    assertFalse(result);
  }

  @Test
  public void testGetBoolFromResults_null() throws Exception {
    List<Object> input = new ArrayList<>();
    input.add(null);
    boolean result = SqlUtils.getBoolFromResults(input,0);
    assertFalse(result);
  }

  @Test
  public void testGetBigDecimalFromResults_positive() throws Exception {
    List<Object> input = new ArrayList<>();
    input.add(BigDecimal.TEN);
    BigDecimal result = SqlUtils.getBigDecimalFromResults(input,0);
    assertEquals(BigDecimal.TEN,result);
  }

  @Test
  public void testGetBigDecimalFromResults_negative() throws Exception {
    List<Object> input = new ArrayList<>();
    input.add(BigDecimal.TEN.negate());
    BigDecimal result = SqlUtils.getBigDecimalFromResults(input,0);
    assertEquals(BigDecimal.TEN.negate(),result);
  }

  @Test
  public void testGetBigDecimalFromResults_null() throws Exception {
    List<Object> input = new ArrayList<>();
    input.add(null);
    BigDecimal result = SqlUtils.getBigDecimalFromResults(input,0);
    assertNull(result);
  }

}
