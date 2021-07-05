@extends('layouts.app')

@section('content')
<div class="">
    <div class="">
        <div class="">
            <div class="">
                <div class="text-3xl pb-5">{{ __('Login') }}</div>

               
                    <form method="POST" action="{{ route('login') }}">
                        @csrf
                        <div class="flex pt-2 pb-2">
                            <input id="email" type="email" placeholder="{{ __('E-Mail Address') }}"class=" @error('email') is-invalid @enderror" name="email" value="{{ old('email') }}" required autocomplete="email" autofocus>

                            @error('email')
                                <span class="invalid-feedback" role="alert">
                                    <strong>{{ $message }}</strong>
                                </span>
                            @enderror
                        </div>
                        <div class="flex pb-2">
                            <input id="password" type="password" placeholder="{{ __('Password') }}" class="form-control @error('password') is-invalid @enderror" name="password" required autocomplete="current-password">

                            @error('password')
                                <span class="invalid-feedback" role="alert">
                                    <strong>{{ $message }}</strong>
                                </span>
                            @enderror
                        </div>              
                        <div >
                            <input class="" type="checkbox" name="remember" id="remember" {{ old('remember') ? 'checked' : '' }}>

                            <label class="" for="remember">
                                {{ __('Remember Me') }}
                            </label>
                        </div>
                        <div class=" pb-2">
                            <button type="" class=" mt-6 shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded">
                                {{ __('Login') }}
                            </button>

                            @if (Route::has('password.request'))
                                <a class="text-indigo-500" href="{{ route('password.request') }}">
                                    {{ __('Forgot Your Password?') }}
                                </a>
                            @endif
                        </div>
                </form>
                
            </div>
        </div>
    </div>
</div>
@endsection
