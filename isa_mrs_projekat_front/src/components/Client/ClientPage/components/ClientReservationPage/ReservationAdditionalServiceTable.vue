<template>
  <div v-if="additionalServices" class="row main-col text-center header rounded mb-3">
    <table style="width: 50vw;">
      <thead>
      <tr>
        <th>Service</th>
        <th>Price</th>
        <th>Add</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(service, index) in additionalServices" :key="index" :class="index%2!==0?'odd':'even'">
        <td v-if="service.price !== 0">
          {{ service.name }}
        </td>
        <td v-if="service.price !== 0">
          {{ service.price }}
        </td>
        <td v-if="service.price !== 0">
          <input class="form-check-input" type="checkbox">
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <div v-else class="row">
    <p class="h6">
      <i>No additional services.</i>
    </p>
  </div>
</template>

<script>
export default {
  name: "ReservationAdditionalServiceTable",
  props: ["additionalServices"]
}
</script>

<style scoped>

</style>