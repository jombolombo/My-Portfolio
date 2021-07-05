<div>
   <form wire:submit.prevent="submit">
        @csrf
       <label for="">
        Difficulty
        <select name="" id="" wire:model="difficulty" class="shadow-sm  border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <option value=""></option>
            <option  value="1" selected>Easy</option>
            <option value="0">Hard</option>
        </select>
       </label>
       <br>
       <input type="submit" class=" mt-6 shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded" value="save changes">
   </form>
   <form wire:submit.prevent="deleteAccount" method="POST">
       @csrf
       <input type="submit" class=" mt-6 shadow-lg bg-red-500 hover:bg-red-700 text-white  py-2 px-4 rounded" value="delete Account">
   </form>
   
</div>
