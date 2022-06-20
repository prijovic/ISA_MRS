<template>
  <div class="card col-xl-6 col-lg-6 col-md-6 col-sm-10 col-xs-12 p-0 m-0 mb-5 align-items-center"
       style="background-color: transparent; box-shadow: none;">
    <div :id="'imgDiv' + client.id" class="card-body p-0 m-0 " style="width: 90%;
              background-size: cover; background-position: center; color: black; height: 45vh; ">
      <div :id="'overlay' + client.id" class="overlay col-12 text-center" style="width: inherit;">
        <p class="h1 cut-text" :title="getFullName" style="cursor: pointer;"><strong>{{ getFullName }}</strong></p>
        <p class="h6 cut-text">{{ this.client.email }}</p>
        <p class="h6 cut-text">{{ this.client.phone }}</p>
        <p class="h5 cut-text" style="cursor: pointer;" :title="getAddress">
          <font-awesome-icon class="me-1" icon="location-dot" style="color: #008970"></font-awesome-icon>
          <small>{{ getAddress }}</small>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
// import axios from "axios";

library.add(faLocationDot);

export default {
  name: "SubscribedClients",
  components: {FontAwesomeIcon},
  props: ["client"],
  data() {
    return {
      photo: null,
    }
  },
  mounted() {
    if(!this.client.photo) { this.setUpProfilePic(); return; }
    axios.get("/Photos/", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token,
      },
      params: {
        path: this.client.photo,
      },
      responseType: "blob"
    })
        .then(response => {
          this.photo = URL.createObjectURL(response.data);
          this.setUpProfilePic();
        })
        .catch((error) => {
          console.log(error);
        });
  },
  methods: {
    setUpProfilePic() {
      let picDiv = document.getElementById('imgDiv' + this.client.id);
      if(!this.photo) {
        picDiv.style.backgroundImage = "url('" + require('../../../../../../Images/noProfilePic.png') + "')";
      }
      else {
        picDiv.style.backgroundImage="url('" + this.photo + "')";
      }
    }
  },
  computed: {
    getFullName() {
      return this.client.name + ' ' + this.client.surname;
    },
    getAddress() {
      return this.client.address.city + ', ' + this.client.address.country;
    },
  }
}
</script>

<style lang="scss" scoped>
a {
  text-decoration: none;
  color: black;
}

a:hover {
  margin-bottom: 5px;
}

.cut-text {
  width: 100%;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

div.card {
  background-color: transparent;
  border-radius: 40px;
  border: none;
  div.overlay {
    position: absolute;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    width: 100%;
    height: 50%;
    padding-top: 1rem;
    padding-left: 1rem;
    padding-right: 1rem;
    color: black;
    background: linear-gradient(
            to bottom,
            rgba($color: white, $alpha: 0) 0%,
            rgba($color: white, $alpha: 1) 100%
    );
    border-bottom-left-radius: 40px;
    border-bottom-right-radius: 40px;
  }
}

div.card-body {
  border-radius: 40px;
  //box-shadow: 0 0.3rem 1rem rgb(0 0 0 / 15%), -0.05em 0 1rem rgb(0 0 0 / 15%);
  box-shadow: 0 0.01rem 1rem darkgray;
}


</style>