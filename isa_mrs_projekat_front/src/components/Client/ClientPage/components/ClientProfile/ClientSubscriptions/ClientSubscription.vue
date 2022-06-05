<template>
  <div v-for="(subscription, i) in subscriptions" :key="i" class="card col-xl-6 col-lg-6 col-md-6 col-sm-10 col-xs-12 px-3 mb-4">
    <!--      <ThePhoto v-if="rental.displayPhoto" class="card-img-top" :photo="rental.displayPhoto" alt=""/>-->
    <router-link :to="getPath(subscription)" class="link" @click="setRentalIdAndType">

      <img v-if="subscription.photos[0].photo" class="card-img-top" alt="" :src="images[i]"/>
    </router-link>
    <div class="card-body pt-1 pb-0">
      <div class="align-items-center text-start" style="display: flex;">
        <div>
          <h1 class="card-title cut-text">{{ subscription.name }}</h1>
        </div>
        <hr class="ms-1">
      </div>
      <div class="row d-flex m-0 p-0 justify-content-center">
        <button :id="subscription.id" :disabled="isSubscriptionCancelled[i]"
                @click="cancelSubscription(i, subscription)"
                class="btn m-0 py-1" :class="[isSubscriptionCancelled[i] ? 'cancelled' : 'cancel']">
          {{ isSubscriptionCancelled[i] ? "Cancelled" : this.btnText }}
        </button>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "ClientSubscription",
  props: ["subscriptions"],
  data() {
    return {
      images: [],
      isSubscriptionCancelled: [],
      btnText: "Cancel Subscription",
    }
  },
  computed: {
    getAccessToken() {
      return store.state.access_token;
    },
  },
  methods: {
    cancelSubscription(i, subscription) {
      axios.post("/RentalObjects/cancelSubscription", { rentalId: subscription.id }, {
        headers: {
          Authorization: "Bearer " + this.getAccessToken,
        },
      })
      .then(() => {
        this.isSubscriptionCancelled[i] = true;
      })
      .catch(error => {
        if (error.response.status === 404) {
          this.$notify({
            title: "Invalid Status",
            text: "Something went wrong. Try again later.",
            position: "bottom right",
            type: "warn"
          })
        } else if (error.response.status === 500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            position: "bottom right",
            type: "error"
          })
        }
      })

    },
    setRentalIdAndType(subscription) {
      this.$store.dispatch("rentalId", subscription.id);
      this.$store.dispatch("rentalType", subscription.rentalObjectType);
    },
    getPath(subscription) {
      return "/client/RentalProfile/" + subscription.rentalObjectType + "-"
          + subscription.id;
    },

  },
  mounted() {
    for (let i=0; i < this.subscriptions.length; i++) {
      this.isSubscriptionCancelled[i] = false;
      if(!this.subscriptions[i].photos[0].photo) { this.images[i] = null; continue; }
      axios.get("/Photos/", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: this.subscriptions[i].photos[0].photo,
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
  },
}
</script>

<style scoped>
button.cancel {
  background-color: #e23c52;
  border: 1px solid #e23c52;
  color: white;
  font-weight: 500;
  border-radius: 40px;
  width: 60%;
}

button.cancelled {
  background-color: lightgray;
  border: 1px solid darkgray;
  color: darkgray;
  font-weight: 500;
  border-radius: 40px;
  width: 60%;
}
/*button.cancel:disabled, button.cancel[disabled] {*/
/*  background-color:lightgray;*/
/*  border: 2px solid darkgray;*/
/*  color: darkgray;*/
/*}*/
.cut-text {
  width: 100%;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
div.card img {
  height: 25vh;
  object-fit: cover;
  object-position: center;
  border-top-left-radius: 40px;
  border-top-right-radius: 40px;
  /*border: 1px solid black;*/
  /*border-bottom: none;*/
}

a {
  text-decoration: none;
  color: black;
}

hr {
  flex-grow: 1;
}

div.card {
  border: none;
}

div.card-body {
  border: 1px solid black;
  border-bottom-left-radius: 40px;
  border-bottom-right-radius: 40px;
  box-shadow: 0 0.3rem 1rem rgb(0 0 0 / 15%);
}

.card-img-top {
  box-shadow: -0.05em 0 1rem rgb(0 0 0 / 15%);
}
</style>