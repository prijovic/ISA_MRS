<template>
  <p class="h4">
    <font-awesome-icon class="me-1" icon="location-dot"></font-awesome-icon><small>{{ getAddress }}</small>
  </p>
  <div class="map-wrap">
    <div class="map" ref="mapContainer"></div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import { Map, NavigationControl, Marker } from 'maplibre-gl';
import { shallowRef, onMounted, onUnmounted, markRaw } from 'vue';

library.add(faLocationDot);

export default {
  name: "RentalAddress",
  components: {FontAwesomeIcon},
  props: ["address"],
  setup (props) {
    const mapContainer = shallowRef(null);
    const map = shallowRef(null);

    onMounted(() => {
      const apiKey = 'nhoszMgknD7huJIF4K2D';

      const initialState = { lng: props.address.longitude, lat: props.address.latitude, zoom: 14 };

      map.value = markRaw(new Map({
        container: mapContainer.value,
        style: `https://api.maptiler.com/maps/streets/style.json?key=${apiKey}`,
        center: [initialState.lng, initialState.lat],
        zoom: initialState.zoom
      }));
      map.value.addControl(new NavigationControl(), 'top-right');
      new Marker({color: "#e23c52"})
          .setLngLat([props.address.longitude, props.address.latitude])
          .addTo(map.value);

    }),
        onUnmounted(() => {
          map.value?.remove();
        })

    return {
      map, mapContainer
    };
  },
  computed: {
    getAddress() {
      return this.address.street + ' ' + this.address.number + ', ' + this.address. city + ', ' + this.address.country
    }
  }
}
</script>

<style scoped>
@import '~maplibre-gl/dist/maplibre-gl.css';

.map-wrap {
  position: relative;
  width: 100%;
  height: 30vh; /* calculate height of the screen minus the heading */
}

.map {
  position: absolute;
  width: 100%;
  height: 100%;
}

</style>