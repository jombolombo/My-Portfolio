<div class=" shadow-sm md:m-4 sm:m-2 lg:m-5 md:p-3 lg:p-4 bg-gray-100 hover:bg-blue-300 hover:shadow-lg hover:animate-pulse" style="width:300px; height: 500px " id="{{$article['id']}}" >
    <img src ={{$article['image']}} alt="Card image" style="width:100%; height: 250px; overflow:hidden;" class="object-contain">
    <div class="p-2">
        <p class=" subpixel-antialiased text-base font-normal">{{\Illuminate\Support\Str::limit($article['summary'], 120, $end='...')}}</p>
        <p class="subpixel-antialiased text-lg font-light italic text-opacity-75 pb-5">-{{$article['source']}}</p>
        <a href="{{$article['url']}}" >
            <button class="shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded">read more</button>
        </a>
    </div>
</div>