<template>
  <div class="row main">
    <div class="accordion accordion-flush" id="accordionFlushExample">
      <div class="accordion-item">
        <h2 class="accordion-header" id="flush-headingThree" style="border: 1px solid black;">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
            <p class="h3"><strong>Special Offers</strong></p>
          </button>
        </h2>
        <div id="flush-collapseThree" class="accordion-collapse collapse show" aria-labelledby="flush-headingThree">
          <div class="accordion-body p-2 pb-1">

            <div v-if="rentalHasSpecialOffers" class="specialOffers">
              <div v-for="(offer, i) in specialOffers" :key="i" class="review p-2 mb-1">
                <div class="offer-header">

                </div>
                <div class="offer-body" style="text-align: justify; color: black;">

                </div>
              </div>


            </div>
            <div v-else class="text-center">
              <label><i>No special offers available.</i></label>
            </div>


          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RentalSpecialOffers",
  props: ["specialOffers"],
  computed: {
    rentalHasSpecialOffers() {
      return true;
      // return this.specialOffers.length > 0;
    },
  }
}
</script>

<style scoped>

</style>