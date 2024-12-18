package org.iesharia.senderismolanzarote.presentation.features.navigation.components

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.iesharia.senderismolanzarote.domain.model.map.MapSettings
import org.iesharia.senderismolanzarote.domain.model.map.NavigationState
import org.iesharia.senderismolanzarote.domain.model.route.main.PointOfInterestModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.iesharia.senderismolanzarote.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.overlay.TilesOverlay

@Composable
fun MapView(
    navigationState: NavigationState,
    mapSettings: MapSettings,
    pointsOfInterest: List<PointOfInterestModel>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            setHorizontalMapRepetitionEnabled(false)
            setVerticalMapRepetitionEnabled(false)
            isTilesScaledToDpi = true
            minZoomLevel = 5.0
            maxZoomLevel = 20.0
        }
    }

    // Efecto para manejar el modo nocturno
    LaunchedEffect(mapSettings.isNightMode) {
        mapView.overlayManager.tilesOverlay.apply {
            if (mapSettings.isNightMode) {
                setColorFilter(TilesOverlay.INVERT_COLORS)
            } else {
                setColorFilter(null)
            }
        }
        mapView.invalidate()
    }

    // Efecto para actualizar los marcadores
    LaunchedEffect(navigationState.currentRoute, pointsOfInterest, navigationState.userLatitude, navigationState.userLongitude) {
        mapView.overlays.clear()

        navigationState.currentRoute?.let { route ->
            // Debug logs para verificar las coordenadas
            Log.d("MapView", "Inicio: ${route.startLatitude}, ${route.startLongitude}")
            Log.d("MapView", "Fin: ${route.endLatitude}, ${route.endLongitude}")

            // Marcador de inicio
            Marker(mapView).apply {
                val startPoint = GeoPoint(route.startLatitude.toDouble(), route.startLongitude.toDouble())
                position = startPoint
                title = "Inicio: ${route.startPoint}"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = context.getDrawable(
                    if (mapSettings.isNightMode) R.drawable.ic_start_marker_night
                    else R.drawable.ic_start_marker_day
                )
            }.also { mapView.overlays.add(it) }

            // Marcador de fin (solo si es diferente al inicio)
            if (!route.isCircular) {
                Marker(mapView).apply {
                    val endPoint = GeoPoint(route.endLatitude.toDouble(), route.endLongitude.toDouble())
                    position = endPoint
                    title = "Fin: ${route.endPoint}"
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    icon = context.getDrawable(
                        if (mapSettings.isNightMode) R.drawable.ic_end_marker_night
                        else R.drawable.ic_end_marker_day
                    )
                }.also { mapView.overlays.add(it) }
            }

            // Línea de la ruta
            Polyline(mapView).apply {
                addPoint(GeoPoint(route.startLatitude.toDouble(), route.startLongitude.toDouble()))
                if (!route.isCircular) {
                    addPoint(GeoPoint(route.endLatitude.toDouble(), route.endLongitude.toDouble()))
                }
                outlinePaint.apply {
                    strokeWidth = 8f
                    color = if (mapSettings.isNightMode) {
                        android.graphics.Color.rgb(100, 200, 255)
                    } else {
                        android.graphics.Color.rgb(0, 102, 204)
                    }
                }
            }.also { mapView.overlays.add(it) }

            // Puntos de interés
            pointsOfInterest.forEachIndexed { index, poi ->
                Log.d("MapView", "POI $index: ${poi.latitude}, ${poi.longitude}")
                Marker(mapView).apply {
                    position = GeoPoint(poi.latitude.toDouble(), poi.longitude.toDouble())
                    title = poi.name
                    snippet = poi.description
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    icon = context.getDrawable(
                        if (mapSettings.isNightMode) R.drawable.ic_poi_marker_night
                        else R.drawable.ic_poi_marker_day
                    )
                }.also { mapView.overlays.add(it) }
            }

            // Ubicación del usuario
            navigationState.userLatitude?.let { lat ->
                navigationState.userLongitude?.let { lon ->
                    Log.d("MapView", "Usuario: $lat, $lon")
                    Marker(mapView).apply {
                        position = GeoPoint(lat.toDouble(), lon.toDouble())
                        title = "Tu ubicación actual"
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        icon = context.getDrawable(
                            if (mapSettings.isNightMode) R.drawable.ic_user_location_night
                            else R.drawable.ic_user_location_day
                        )
                    }.also { mapView.overlays.add(it) }
                }
            }

            // Centrar y ajustar zoom
            val points = mutableListOf<GeoPoint>()
            points.add(GeoPoint(route.startLatitude.toDouble(), route.startLongitude.toDouble()))
            if (!route.isCircular) {
                points.add(GeoPoint(route.endLatitude.toDouble(), route.endLongitude.toDouble()))
            }
            pointsOfInterest.forEach { poi ->
                points.add(GeoPoint(poi.latitude.toDouble(), poi.longitude.toDouble()))
            }
            navigationState.userLatitude?.let { lat ->
                navigationState.userLongitude?.let { lon ->
                    points.add(GeoPoint(lat.toDouble(), lon.toDouble()))
                }
            }

            // Calculamos el centro y el zoom apropiado
            if (points.isNotEmpty()) {
                val bounds = BoundingBox.fromGeoPoints(points)
                mapView.zoomToBoundingBox(bounds.increaseByScale(1.2f), true, 100)
            }
        }

        mapView.invalidate()
    }

    // Cleanup
    DisposableEffect(mapView) {
        onDispose {
            mapView.onDetach()
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier
    )
}
