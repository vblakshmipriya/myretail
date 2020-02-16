package com.world.myretail

import org.skyscreamer.jsonassert.FieldComparisonFailure
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.JSONCompareResult
import org.skyscreamer.jsonassert.comparator.DefaultComparator

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper


class JsonUtils {

  static final ObjectMapper MAPPER = new ObjectMapper()
  static List nulls = ['null', null]

  static {
    MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
    MAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
  }

  static void assertJsonEquals(Object expectedMapOrList, Object actualMapOrList) {
    assertJsonEquals(MAPPER.writeValueAsString(expectedMapOrList), MAPPER.writeValueAsString(actualMapOrList))
  }

  static void assertJsonEquals(String expected, String actual) {
    if (actual in nulls || expected in nulls) {
      assert actual == expected: "${actual} != ${expected}"
      return
    }

    JSONCompareResult result = JSONCompare.compareJSON(expected, actual, new DefaultComparator(JSONCompareMode.NON_EXTENSIBLE))
    if (result.failureOnField) {
      Map<String, FieldComparisonFailure> fields = result.fieldFailures.groupBy {
        (it.expected as String).startsWith('regex:')
      }

      fields[true].each {
        AssertionUtils.assertMatch(it.expected as String, it.actual as String)
      }

      if (fields[false]) {
        throw new AssertionError(fields[false].collect {
          "${it.field}: Expected '${it.expected}' != Actual '${it.actual}'"
        })
      }
      String message = result.message
      result.fieldFailures.each {
        message -= result.formatFailureMessage(it.field, it.expected, it.actual)
        message -= ' ; '
      }
      if (message) {
        throw new AssertionError(message.replaceAll('\\n', ' ').replaceAll(' ; ', '\n'))
      }
    } else if (result.failed()) {
      throw new AssertionError(result.message.replaceAll('\\n', ' ').replaceAll('\\s+', ' ').replaceAll('; ', '\n'))
    }
  }
}
