<div>
  @section('title', 'LeaderBoard')
    <h1 class="text-3xl pb-5 font-medium tracking-tight">leaderboard</h1>
  <table>
      <thead>
        <tr>
            <th class=" p-2 border-2 font-medium border-blue-300">position</th>
            <th class=" p-2 border-2 font-medium border-blue-300">Username</th>
            <th class=" p-2 border-2 font-medium border-blue-300">accountvalue</th>
        </tr>
      </thead>
      <tbody>
          <?php $x = 0; ?>
        @foreach ($leaderboard as $item)
            <tr>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$x+1}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$usernames[$x][0]["username"]}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$item["accountvalue"]}}</td>
            </tr>
            <?php $x++; ?>
        @endforeach
      </tbody>
  </table>
  @if ( auth()->user()->difficulty ==="easy")
    <div class="mt-5 bg-white p-5 shadow-inner rounded">
        <h1  class="text-2xl pb-2">tip</h1>
        <p>{!! nl2br($tip) !!}</p>
    </div>
  @endif
</div>
