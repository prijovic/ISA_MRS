<template>
  <div class="row mb-3">
    <p class="h6 ps-0 pb-0"><i>Street:</i></p>
    <input v-model="street" disabled class="ps-1 h5 disabled" type="text" id="country" placeholder="">
  </div>
  <div class="row mb-3">
    <p class="h6 ps-0 pb-0"><i>Place:</i></p>
    <input v-model="city" disabled class="ps-1 h5 disabled" type="text" id="city" placeholder="">
  </div>
  <div class="row mb-3">
    <p class="h6 ps-0 pb-0"><i>Country:</i></p>
    <input v-model="country" disabled class="ps-1 h5 disabled" type="text" id="street" placeholder="">
  </div>
</template>

<script>
export default {
  name: "AddressLookup",
  data() {
    return {
      street: "",
      city: "",
      country: ""
    };
  },
  props: {
    query: String
  },
  watch: {
    query: function(value) {
      fetch(`https://autocomplete.geocoder.api.here.com/6.2/suggest.json?app_id=APP_ID_HERE&app_code=APP_CODE_HERE&maxresults=1&query=${value}`)
          .then(result => result.json())
          .then(result => {
            if(result.suggestions && result.suggestions.length > 0) {
              if(result.suggestions[0].address.houseNumber && result.suggestions[0].address.street) {
                this.street = result.suggestions[0].address.houseNumber + " " + result.suggestions[0].address.street;
              } else {
                this.street = "";
              }
              this.city = result.suggestions[0].address.city ? result.suggestions[0].address.city : "";
              this.country = result.suggestions[0].address.country ? result.suggestions[0].address.country : "";
            } else {
              this.street = "";
              this.city = "";
              this.country = "";
            }
          }, error => {
            console.error(error);
          });
    }
  },
  methods: {
    validate: function(query) {
      return fetch(`https://geocoder.api.here.com/6.2/geocode.json?app_id=APP_ID_HERE&app_code=APP_CODE_HERE&searchtext=${query}`)
          .then(result => result.json())
          .then(result => {
            if(result.Response.View.length > 0 && result.Response.View[0].Result.length > 0) {
              let data = result.Response.View[0].Result[0];
              return data;
            }
          }, error => {
            console.error(error);
          });
    }
  }
}
</script>

<style scoped>
  input[type='text'] {
    width: 100%;
    font-weight: 100;
  }

  input::placeholder {
    color: grey;
    font-weight: 100;
  }

  input.disabled {
    background-color: lightgrey;
  }
</style>