package yukams.app.background_locator_2.provider

import android.location.Location
import android.os.Build
import com.google.android.gms.location.LocationResult
import yukams.app.background_locator_2.Keys

class LocationParserUtil {
    companion object {
        fun getLocationMapFromLocation(location: Location): HashMap<Any, Any> {
            val speedAccuracy = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> location.speedAccuracyMetersPerSecond
                else -> 0f
            }
            
            val isMocked = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                location.isMock
            } else {
                @Suppress("DEPRECATION")
                location.isFromMockProvider
            }

            return hashMapOf(
                Keys.ARG_IS_MOCKED to isMocked,
                Keys.ARG_LATITUDE to location.latitude,
                Keys.ARG_LONGITUDE to location.longitude,
                Keys.ARG_ACCURACY to location.accuracy,
                Keys.ARG_ALTITUDE to location.altitude,
                Keys.ARG_SPEED to location.speed,
                Keys.ARG_SPEED_ACCURACY to speedAccuracy,
                Keys.ARG_HEADING to location.bearing,
                Keys.ARG_TIME to location.time.toDouble(),
                Keys.ARG_PROVIDER to (location.provider ?: "unknown")
            )
        }

        fun getLocationMapFromLocation(locationResult: LocationResult?): HashMap<Any, Any>? {
            val location = locationResult?.lastLocation ?: return null
            return getLocationMapFromLocation(location)
        }
    }
}