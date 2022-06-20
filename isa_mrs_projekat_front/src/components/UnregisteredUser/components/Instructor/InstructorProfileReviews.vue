<template>
  <div v-if="reviews" class="row">
    <div class="col-12 d-flex justify-content-center align-items-center">
      <star-rating class="mb-5" text-class="h1 my-0 font-weight-normal" :round-start-rating="false"
                   :glow="5" glow-color="#ffd055" :star-size="75" :read-only="true" :rating="grade">
      </star-rating>
    </div>
    <div class="row p-0 m-0">
      <div v-if="ownerHasReviews" class="reviews">
        <div v-for="(review, i) in this.sortedReviews" :key="i" class="review p-2 mb-3" style="background-color: #e4f6f8;">
          <div class="review-header">
            <div class="align-items-center" style="display:flex; justify-content: space-between; ">
              <div style="display: flex;">
                <img v-if="images[i]" class="profile-pic rounded-pill" :src="images[i]" alt=""/>
                <img v-else class="profile-pic rounded-pill" src="../../../Images/noProfilePic.png" alt=""/>
                <div class="ms-1">
                  <h6>{{ getAuthorFullName(review) }}</h6>
                  <p class="small" style="color: gray;">{{ formatReviewDate(review.timeStamp) }}</p>
                </div>
              </div>
              <p class="h3 pe-2">{{ review.grade + "★" }}</p>
            </div>
          </div>
          <div class="review-body" style="text-align: justify; color: black;">
            <p v-if="review.comment" class="ps-1 pe-2">{{ review.comment }}</p>
          </div>
        </div>
      </div>
      <div v-else class="text-center">
        <label><i>No reviews available.</i></label>
      </div>
    </div>
  </div>
</template>

<script>
import StarRating from "vue-star-rating";
import axios from "axios";

export default {
  name: "InstructorProfileReviews",
  props: ["reviews", "grade"],
  components: {StarRating},
  data() {
    return {
      images: [],
      sortedReviews: [],
    }
  },
  computed: {
    ownerHasReviews() {
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
  },
  mounted() {
    this.sortedReviews = this.reviews;
    this.sortedReviews = this.sortedReviews.sort((a, b) => new Date(b.timeStamp) > new Date(a.timeStamp) ? 1: -1);
    this.images = [];
    for(let i=0; i<this.sortedReviews.length; i++) {
      if(!this.reviews[i].author.photo) { this.images[i] = null; continue; }
      axios.get("/Photos/", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: this.reviews[i].author.photo,
        },
        responseType: "blob"
      })
      .then(response => {
        this.images[i] = URL.createObjectURL(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    }
  }
}
</script>

<style scoped>

hr {
  flex-grow: 1;
}

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

div.review {
  background-color: white
}
</style>