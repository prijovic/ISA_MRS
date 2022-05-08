<template>
  <div class="col main pt-3">
    <div class="row">
      <div class="col-1 main"></div>
      <div class="col-4 main">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Name:</i></p>
          <input class="ps-1 h5" type="text" id="name" placeholder="Input name...">
        </div>
        <div class="row">
          <p class="h4 p-0"><font-awesome-icon icon="house"></font-awesome-icon> Address</p>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Country:</i></p>
          <input class="ps-1 h5" type="text" id="country" placeholder="Country">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>City:</i></p>
          <input class="ps-1 h5" type="text" id="city" placeholder="City">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Street:</i></p>
          <input class="ps-1 h5" type="text" id="street" placeholder="Street">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Number:</i></p>
          <input class="ps-1 h5" type="number" id="number" placeholder="House Number">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Description:</i></p>
          <textarea class="ps-1 h5" maxlength="255" rows="3" placeholder="Input description..." style="resize: none"></textarea>
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Photos:</i></p>
          <input class="p-0 h5" type="file" name="photos" @change="onFileSelected" accept="image/jpeg" multiple="multiple"/>
          <small class="p-0" style="color: grey;">Acceptable: jpg</small>
        </div>
      </div>
      <div class="col-2 main"></div>
      <div class="col-4 main">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Capacity:</i></p>
          <input class="ps-1 h5" type="number" placeholder="Input capacity...">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Price:</i></p>
          <input class="ps-1 h5" type="number" placeholder="Input price...">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Cancellation fee:</i></p>
          <div class="col">
            <select class="form-control">
              <option value="Free" selected="selected">Free</option>
              <option value="Fixed">Fixed</option>
              <option value="Percentile">Percentile</option>
            </select>
          </div>
          <div class="col">
            <input class="ps-1 h5" type="number" placeholder="Input value...">
          </div>
        </div>
        <button id="subscribe" type="button" class="btn btn-lg btn-default" data-toggle="popover">Additional services</button>
        <div id="popover-form" class="hide">
          <form id="additionalServices" class="form-inline" role="form">
            <div class="form-group">
              <input id="email" type="email" placeholder="Enter Email-ID" class="form-control" />
            </div>
            <button type="submit" class="btn btn-warning">Subscribe</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHouse } from "@fortawesome/free-solid-svg-icons";

library.add(faHouse);

export default {
  name: "RentalObjectAdditionForm",
  components: {
    FontAwesomeIcon
  }
}
</script>

<style scoped>

</style>