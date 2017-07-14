package com.mapbox.services.android.telemetry.navigation;

import android.location.Location;

import com.mapbox.services.android.telemetry.constants.TelemetryConstants;
import com.mapbox.services.android.telemetry.utils.TelemetryUtils;

import java.util.Date;
import java.util.Hashtable;

/**
 * Represent events dispatched by the Navigation SDK.
 */
public class MapboxNavigationEvent {

  private static final int EVENT_VERSION = 1;

  // Event types
  public static final String TYPE_TURNSTILE = "navigation.turnstile";
  public static final String TYPE_DEPART = "navigation.depart";
  public static final String TYPE_FEEDBACK = "navigation.feedback";
  public static final String TYPE_ARRIVE = "navigation.arrive";
  public static final String TYPE_CANCEL = "navigation.cancel";

  // Event keys
  public static final String KEY_EVENT = "event";
  public static final String KEY_OPERATING_SYSTEM = "operatingSystem";
  public static final String KEY_SDK_IDENTIFIER = "sdkIdentifier";
  public static final String KEY_SDK_VERSION = "sdkVersion";
  public static final String KEY_EVENT_VERSION = "eventVersion";
  public static final String KEY_SESSION_IDENTIFIER = "sessionIdentifier";
  public static final String KEY_LAT = "lat";
  public static final String KEY_LNG = "lng";
  public static final String KEY_GEOMETRY = "geometry";
  public static final String KEY_CREATED = "created";
  public static final String KEY_PROFILE = "profile";
  public static final String KEY_ESTIMATED_DISTANCE = "estimatedDistance";
  public static final String KEY_ESTIMATED_DURATION = "estimatedDuration";
  public static final String KEY_REROUTE_COUNT = "rerouteCount";
  public static final String KEY_VOLUME_LEVEL = "volumeLevel";
  public static final String KEY_SCREEN_BRIGHTNESS = "screenBrightness";
  public static final String KEY_APPLICATION_STATE = "applicationState";
  public static final String KEY_BATTERY_PLUGGED_IN = "batteryPluggedIn";
  public static final String KEY_BATTERY_LEVEL = "batteryLevel";
  public static final String KEY_CONNECTIVITY = "connectivity";
  public static final String KEY_FEEDBACK_TYPE = "feedbackType";
  public static final String KEY_LOCATIONS_BEFORE = "locationsBefore";
  public static final String KEY_LOCATIONS_AFTER = "locationsAfter";
  public static final String KEY_NEW_DISTANCE_REMAINING = "newDistanceRemaining";
  public static final String KEY_NEW_DURATION_REMAINING = "newDurationRemaining";
  public static final String KEY_START_TIMESTAMP = "startTimestamp";
  public static final String KEY_DISTANCE_COMPLETED = "distanceCompleted";
  public static final String KEY_DISTANCE_REMAINING = "distanceRemaining";
  public static final String KEY_DURATION_REMAINING = "durationRemaining";
  public static final String KEY_SECONDS_SINCE_LAST_REROUTE = "secondsSinceLastReroute";

  /**
   * Navigation turnstile.
   */
  public static Hashtable<String, Object> buildTurnstileEvent(String sdKIdentifier, String sdkVersion) {
    Hashtable<String, Object> event = new Hashtable<>();
    event.put(KEY_EVENT, TYPE_TURNSTILE);
    event.put(KEY_OPERATING_SYSTEM, TelemetryConstants.OPERATING_SYSTEM);
    event.put(KEY_SDK_IDENTIFIER, sdKIdentifier);
    event.put(KEY_SDK_VERSION, sdkVersion);
    return event;
  }

  /**
   * User started a route.
   */
  public static Hashtable<String, Object> buildDepartEvent(
      String sdKIdentifier, String sdkVersion, String sessionIdentifier, double lat, double lng,
      String geometry, String profile, int estimatedDistance, int estimatedDuration,
      int rerouteCount) {
    Hashtable<String, Object> event = getMetadata(sdKIdentifier, sdkVersion, sessionIdentifier,
        lat, lng, geometry, profile, estimatedDistance, estimatedDuration, rerouteCount);
    event.put(KEY_EVENT, TYPE_DEPART);
    return event;
  }

  /**
   * User feedback/reroute event.
   */
  public static Hashtable<String, Object> buildFeedbackEvent(
      String sdKIdentifier, String sdkVersion, String sessionIdentifier, double lat, double lng,
      String geometry, String profile, int estimatedDistance, int estimatedDuration,
      int rerouteCount, Date startTimestamp, String feedbackType,
      Location[] locationsBefore, Location[] locationsAfter, int distanceCompleted,
      int distanceRemaining, int durationRemaining, int newDistanceRemaining,
      int newDurationRemaining, int secondsSinceLastReroute) {
    Hashtable<String, Object> event = getMetadata(sdKIdentifier, sdkVersion, sessionIdentifier,
        lat, lng, geometry, profile, estimatedDistance, estimatedDuration, rerouteCount);
    event.put(KEY_EVENT, TYPE_FEEDBACK);
    event.put(KEY_START_TIMESTAMP, TelemetryUtils.generateCreateDateFormatted(startTimestamp));
    event.put(KEY_FEEDBACK_TYPE, feedbackType);
    event.put(KEY_LOCATIONS_BEFORE, locationsBefore);
    event.put(KEY_LOCATIONS_AFTER, locationsAfter);
    event.put(KEY_DISTANCE_COMPLETED, distanceCompleted);
    event.put(KEY_DISTANCE_REMAINING, distanceRemaining);
    event.put(KEY_DURATION_REMAINING, durationRemaining);
    if (feedbackType.equals("reroute")) {
      event.put(KEY_NEW_DISTANCE_REMAINING, newDistanceRemaining);
      event.put(KEY_NEW_DURATION_REMAINING, newDurationRemaining);
      event.put(KEY_SECONDS_SINCE_LAST_REROUTE, secondsSinceLastReroute);
    }
    return event;
  }

  /**
   * User arrived.
   */
  public static Hashtable<String, Object> buildArriveEvent(
      String sdKIdentifier, String sdkVersion, String sessionIdentifier, double lat, double lng,
      String geometry, String profile, int estimatedDistance, int estimatedDuration,
      int rerouteCount, Date startTimestamp, int distanceCompleted) {
    Hashtable<String, Object> event = getMetadata(sdKIdentifier, sdkVersion, sessionIdentifier,
        lat, lng, geometry, profile, estimatedDistance, estimatedDuration, rerouteCount);
    event.put(KEY_EVENT, TYPE_ARRIVE);
    event.put(KEY_START_TIMESTAMP, TelemetryUtils.generateCreateDateFormatted(startTimestamp));
    event.put(KEY_DISTANCE_COMPLETED, distanceCompleted);
    return event;
  }

  /**
   * User canceled navigation.
   */
  public static Hashtable<String, Object> buildCancelEvent(
      String sdKIdentifier, String sdkVersion, String sessionIdentifier, double lat, double lng,
      String geometry, String profile, int estimatedDistance, int estimatedDuration,
      int rerouteCount, Date startTimestamp, int distanceCompleted, int distanceRemaining,
      int durationRemaining) {
    Hashtable<String, Object> event = getMetadata(sdKIdentifier, sdkVersion, sessionIdentifier,
        lat, lng, geometry, profile, estimatedDistance, estimatedDuration, rerouteCount);
    event.put(KEY_EVENT, TYPE_CANCEL);
    event.put(KEY_START_TIMESTAMP, TelemetryUtils.generateCreateDateFormatted(startTimestamp));
    event.put(KEY_DISTANCE_COMPLETED, distanceCompleted);
    event.put(KEY_DISTANCE_REMAINING, distanceRemaining);
    event.put(KEY_DURATION_REMAINING, durationRemaining);
    return event;
  }

  /**
   * The following metadata should be attached to all non-turnstile events. The Navigation SDK is
   * in charge of keeping track of a navigation sessionIdentifier, and it should use
   * {@link TelemetryUtils#buildUUID()} to generate the random UUID.
   */
  private static Hashtable<String, Object> getMetadata(
      String sdKIdentifier, String sdkVersion, String sessionIdentifier, double lat, double lng,
      String geometry, String profile, int estimatedDistance, int estimatedDuration,
      int rerouteCount) {
    Hashtable<String, Object> event = new Hashtable<>();
    event.put(KEY_OPERATING_SYSTEM, TelemetryConstants.OPERATING_SYSTEM);
    event.put(KEY_SDK_IDENTIFIER, sdKIdentifier);
    event.put(KEY_SDK_VERSION, sdkVersion);
    event.put(KEY_EVENT_VERSION, EVENT_VERSION);
    event.put(KEY_SESSION_IDENTIFIER, sessionIdentifier);
    event.put(KEY_LAT, lat);
    event.put(KEY_LNG, lng);
    event.put(KEY_GEOMETRY, geometry);
    event.put(KEY_CREATED, TelemetryUtils.generateCreateDate(null));
    event.put(KEY_PROFILE, profile);
    event.put(KEY_ESTIMATED_DISTANCE, estimatedDistance);
    event.put(KEY_ESTIMATED_DURATION, estimatedDuration);
    event.put(KEY_REROUTE_COUNT, rerouteCount);
    return event;
  }
}
