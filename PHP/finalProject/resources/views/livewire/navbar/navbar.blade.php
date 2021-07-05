<div>
    <nav class=" container mx-auto flex justify-between">   

        <a class="font-bold " href="{{ url('/') }}">
        FinTech
        </a>

        <div  >
           
            <!-- Right Side Of Navbar -->
            <ul class="flex flex-row">
                <li class="pr-2 ">
                    @if (Route::is("news.index"))
                        <a class="font-bold p-3  text-white hover:bg-indigo-200 hover:shadow-md" href="{{ route('news.index') }}">News</a>
                    @else
                        <a class="font-bold p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('news.index') }}">News</a>
                    @endif
                    
                </li>
                <li class="pr-2">
                    @if (Route::is("company"))
                        <a class="font-bold text-white p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('company') }}">Companies</a>
                    @else
                        <a class="font-bold  p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('company') }}">Companies</a>
                    @endif
                </li>
                <!-- Authentication Links -->
                @guest
                    @if (Route::has('login'))
                            @if (Route::is('login'))
                                <li class="pr-2">
                                    <a class="font-bold text-white  p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('login') }}">{{ __('Login') }}</a>
                                </li>
                            @else
                                <li class="pr-2">
                                    <a class="font-bold  p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('login') }}">{{ __('Login') }}</a>
                                </li>
                            @endif
                    @endif
                    
                    @if (Route::has('register'))
                                @if (Route::is('register'))
                                    <li class="pr-2">
                                        <a class="font-bold  p-3 text-white hover:bg-indigo-200 hover:shadow-md" href="{{ route('register') }}">{{ __('Register') }}</a>
                                    </li>
                                @else
                                    <li class="pr-2">
                                        <a class="font-bold  p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('register') }}">{{ __('Register') }}</a>
                                    </li>
                                @endif
                    @endif
                @else
                        <li class="pr-2">
                            @if (Route::is("profile") || Route::is("trade") || Route::is("leaderboard") || Route::is("settings"))
                                <a role="button" class="font-bold  p-3  text-white hover:bg-indigo-200 hover:shadow-md" id="options-menu" aria-expanded="false" aria-haspopup="true" aria-hidden="true" onclick="showDropdown()">profile</a>
                            @else
                                <a role="button" class="font-bold  p-3 hover:bg-indigo-200 hover:shadow-md" id="options-menu" aria-expanded="false" aria-haspopup="true" aria-hidden="true" onclick="showDropdown()">profile</a>
                            @endif
                            <div class="hidden origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none" id="menu" role="menu" data-track='hover' aria-orientation="vertical" aria-labelledby="options-menu">
                                <div class="py-1" role="none">
                                    <a href="{{route('profile')}}" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" role="menuitem">User Profile</a>
                                    <a href="{{route('trade')}}" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" role="menuitem">trade</a>
                                    <a href="{{route('leaderboard')}}" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" role="menuitem">leaderboard</a>
                                    <a href="{{route('tradeHistory')}}" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" role="menuitem">trade history</a>
                                    <a href="{{route('settings')}}" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" role="menuitem">Settings</a>
                                </div>
                                <script>
                                    function showDropdown() {
                                        var temp =  document.getElementById("menu").style.display;
                                        if (temp === "" || temp === "none"){
                                            document.getElementById("menu").style.display = "block";
                                            setTimeout(removeDropdown, 3500);
                                        } else if (temp === "block") {
                                            document.getElementById("menu").style.display = "none";
                                        }
                                        
                                    }

                                    function removeDropdown() {
                                        document.getElementById("menu").style.display = "none";
                                    }
                                </script>

                            </div>
                        </li>
                    <li class="pr-2">
                        <div class="">
                            <a class="font-bold  p-3 hover:bg-indigo-200 hover:shadow-md" href="{{ route('logout') }}"
                            onclick="event.preventDefault();
                                            document.getElementById('logout-form').submit();">
                                {{ __('Logout') }}
                            </a>

                            <form id="logout-form" action="{{ route('logout') }}" method="POST" class="d-none">
                                @csrf
                            </form>
                        </div>
                    </li>
                @endguest
            </ul>
        </div>
  
</nav>
</div>
