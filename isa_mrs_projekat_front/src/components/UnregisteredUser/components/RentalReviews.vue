<template>
  <div class="row main">
    <div class="accordion accordion-flush" id="accordionFlushExample">
      <div class="accordion-item">
        <h2 class="accordion-header" id="flush-headingTwo" style="border: 1px solid black;">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
            <p class="h3"><strong>Reviews</strong></p>
          </button>
        </h2>
        <div id="flush-collapseTwo" class="accordion-collapse collapse show" aria-labelledby="flush-headingTwo">
          <div class="accordion-body p-2 pb-1">
            <div v-if="rentalHasReviews" class="reviews">



              <div v-for="(review, i) in reviews.slice().reverse()" :key="i" class="review p-2 mb-1">
                <div class="review-header">
                  <div class="align-items-center" style="display:flex; justify-content: space-between; ">
                    <div style="display: flex;">
                      <img class="profile-pic rounded-pill" :src="'https://bootdey.com/img/Content/avatar/avatar3.png'" alt=""/>
                      <div class="ms-1">
                        <h6>{{ getAuthorFullName(review) }}</h6>
                        <p class="small" style="color: gray;">{{ formatReviewDate(review.timeStamp) }}</p>
                      </div>
                    </div>
                    <p class="h3 pe-2">{{ review.grade + "★" }}</p>
                  </div>
                </div>
                <div class="review-body" style="text-align: justify; color: black;">
                  <p class="ps-1 pe-2">{{ review.comment }}</p>
                </div>
              </div>

            </div>
            <div v-else class="text-center">
              <label><i>No reviews available.</i></label>
            </div>


          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RentalReviews",
  props: ["reviews"],
  computed: {
    rentalHasReviews() {
      return this.reviews.length > 0;
    }
  },
  methods: {
    getAuthorFullName(review) {
      return review.author.name + " " + review.author.surname;
    },
    formatReviewDate(reviewTime) {
      let date = new Date(reviewTime);
      return date.getDate() + '.' + (date.getMonth() + 1) + '.' +  date.getFullYear() + '. at '
          + date.getHours() + ':' + ( (date.getMinutes()<10?'0':'') + date.getMinutes() );
    },
  }
}
</script>

<style scoped>
.review .profile-pic {
  width: 50px;
  height: 50px;
  object-fit: cover;
  object-position: center;
}

div.review {
  border-radius: 25px;
  border: 1px solid darkgray;
}

div.review:hover {
  border: 3px solid black;
}

div.accordion-body {
  max-height: 70vh;
  overflow-y: auto;
  background-color: lightgray;
  border-bottom: 1px solid lightgrey;
}

div.review {
  background-color: white
}
</style>